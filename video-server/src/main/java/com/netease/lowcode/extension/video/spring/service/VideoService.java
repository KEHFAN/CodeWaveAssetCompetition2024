package com.netease.lowcode.extension.video.spring.service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.netease.lowcode.extension.video.spring.config.VideoConfig;
import com.netease.lowcode.extension.video.spring.io.PartialFileResource;
import com.netease.lowcode.extension.video.spring.model.Video;
import com.netease.lowcode.extension.video.spring.model.VideoInfo;
import com.netease.lowcode.extension.video.spring.utils.StringGenerator;
import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.util.LimitedInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class VideoService {
    @Autowired
    private VideoConfig videoConfig;

    private String sliceDir;

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
    }

    public String sliceVideo() throws IOException {
        JSONObject jsonObject = new JSONObject();

        // 获取原视频
        String videoPath = "C:\\Users\\fankehu\\Pictures\\4jPEHXdG_9209150941_uhd.mp4";
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

    public Video getVideo(String key, long start, long end) throws IOException {
        VideoInfo videoInfo = getVideoInfo(key);
        Collections.sort(videoInfo.getSlice());

        Video video = new Video();
        video.setStart(start);

        for (Long offset : videoInfo.getSlice()) {
            if (start - offset + 1 > videoInfo.getChunkUnit() * videoInfo.getChunkSize()) {
                continue;
            }
            // 计算该chunk可读取字节
            long chunkEndOff = offset + videoInfo.getChunkSize() * videoInfo.getChunkUnit() - 1;

            // 假设chunk剩余字节为left
            // 1. end-start > left , return left,修改end
            // 2. end-start <=left , return end-start
            if (end > chunkEndOff) {
                end = chunkEndOff;
            }
            video.setEnd(end);
            PartialFileResource partialFileResource = new PartialFileResource(String.join("/", sliceDir, key, String.valueOf(offset)), start, end);
            video.setResource(partialFileResource);
            return video;
        }

        throw new RuntimeException("视频资源读取异常");
    }
}
