package com.netease.lowcode.extension.video.spring.service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.netease.lowcode.extension.video.spring.config.VideoConfig;
import com.netease.lowcode.extension.video.spring.io.SequenceFileResource;
import com.netease.lowcode.extension.video.spring.model.Video;
import com.netease.lowcode.extension.video.spring.model.VideoInfo;
import com.netease.lowcode.extension.video.spring.utils.FileUtil;
import com.netease.lowcode.extension.video.spring.utils.StringGenerator;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

@Service
public class VideoService {
    @Autowired
    private VideoConfig videoConfig;

    private String sliceDir;
    private String originDir;

    public void initDir() {
        // 创建目录
        File baseDir = new File(videoConfig.getBaseDir());
        if (!baseDir.exists() || !baseDir.isDirectory()) {
            baseDir.mkdirs();
        }
        // 存放切片目录
        File sliceDir = new File(baseDir, "/slice");
        if (!sliceDir.exists() || !sliceDir.isDirectory()) {
            sliceDir.mkdirs();
        }
        this.sliceDir = sliceDir.getPath();
        // 存放原始视频
        File originDir = new File(baseDir, "/origin");
        if(!originDir.exists() || !originDir.isDirectory()) {
            originDir.mkdirs();
        }
        this.originDir = originDir.getPath();
    }

    public String sliceVideo(String url,String filename) throws IOException {

        FileUtil.saveFile(url, String.join("/", originDir, filename));

        JSONObject jsonObject = new JSONObject();

        // 获取原视频
        String videoPath = String.join("/", originDir, filename);
        jsonObject.put("fileSize", Files.size(Paths.get(videoPath)));
        jsonObject.put("filename", FilenameUtils.getName(videoPath));
        jsonObject.put("chunkUnit", videoConfig.getChunkUnit());
        jsonObject.put("chunkSize", videoConfig.getChunkSize());

        String generator = StringGenerator.generator(videoConfig.getRandomStringLen());
        String key = generator + "_" + System.currentTimeMillis() + "." + FilenameUtils.getExtension(videoPath);
        jsonObject.put("key", key);

        // 对视频进行切片
        FileInputStream fis = new FileInputStream(videoPath);
        File videoSliceDir = new File(sliceDir, key);
        if (videoSliceDir.exists()) {
            videoSliceDir.delete();
        }
        videoSliceDir.mkdirs();

        JSONArray sliceArray = new JSONArray();
        jsonObject.put("slice", sliceArray);
        // 切片名称为 range 起始offset
        FileOutputStream outputStream = new FileOutputStream(new File(videoSliceDir, "0"));
        sliceArray.add(0);

        int read, count = 0, chunkCount = 0;
        byte[] buffer = new byte[videoConfig.getChunkUnit()];
        while ((read = fis.read(buffer, 0, buffer.length)) != -1) {
            // 写入下一个chunk
            if (count >= videoConfig.getChunkSize()) {
                count = 0;
                chunkCount++;
                outputStream.flush();
                outputStream.close();
                long chunkLen = chunkCount * videoConfig.getChunkUnit() * videoConfig.getChunkSize();
                outputStream = new FileOutputStream(new File(videoSliceDir, String.valueOf(chunkLen)));
                sliceArray.add(chunkLen);
            }
            // 写在同一个chunk
            outputStream.write(buffer, 0, read);
            count++;
        }

        // 写入配置文件
        BufferedWriter writer = new BufferedWriter(new FileWriter(sliceDir + "/" + key + ".json"));
        writer.write(jsonObject.toJSONString());
        writer.flush();
        writer.close();

        return key;
    }

    public VideoInfo getVideoInfo(String key) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(sliceDir + "/" + key + ".json"));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return JSONObject.parseObject(sb.toString(), VideoInfo.class);
    }

    public Video getVideo(String key, long start) throws IOException {
        VideoInfo videoInfo = getVideoInfo(key);
        Collections.sort(videoInfo.getSlice());

        Video video = new Video();
        video.setStart(start);

        for (Long offset : videoInfo.getSlice()) {
            if (start - offset + 1 > videoInfo.getChunkUnit() * videoInfo.getChunkSize()) {
                continue;
            }
            // 计算该chunk结束偏移量
            long chunkEndOff = offset + videoInfo.getChunkSize() * videoInfo.getChunkUnit() - 1;

            // 不允许跨分片加载
            video.setEnd(chunkEndOff);

            SequenceFileResource partialFileResource = new SequenceFileResource(String.join("/", sliceDir, key, String.valueOf(offset)), offset, start, chunkEndOff);
            video.setResource(partialFileResource);
            return video;
        }

        throw new RuntimeException("视频资源读取异常");
    }
}
