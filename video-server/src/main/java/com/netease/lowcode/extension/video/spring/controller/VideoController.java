package com.netease.lowcode.extension.video.spring.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.netease.lowcode.extension.video.spring.config.VideoConfig;
import com.netease.lowcode.extension.video.spring.io.PartialFileResource;
import com.netease.lowcode.extension.video.spring.service.ChunkService;
import com.netease.lowcode.extension.video.spring.utils.StringGenerator;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

@RestController
public class VideoController {

    @Autowired
    private VideoConfig videoConfig;
    @Autowired
    private ChunkService chunkService;


    @GetMapping("/rest/video/slice")
    public ResponseEntity<String> sliceVideo(HttpServletRequest request) throws IOException {

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

        return ResponseEntity.ok()
                .body(
                        request.getScheme() + "://" +
                                request.getServerName() + ":" + request.getServerPort() +
                                "/rest/video/get/" + key);
    }

    @CrossOrigin(allowCredentials = "true",methods = {RequestMethod.GET},origins = "*")
    @GetMapping("/rest/video/get/4jPEHXdG_9209150941_uhd.mp4")
    public ResponseEntity<Resource> getVideo(@RequestHeader(value = "Range", required = false) String range) throws IOException {


        String file = "C:\\Users\\fankehu\\Pictures\\4jPEHXdG_9209150941_uhd.mp4";
        long size = Files.size(Paths.get(file));

        // 返回完整视频资源
        if (Objects.isNull(range) || !range.startsWith("bytes=")) {

            return ResponseEntity.ok()
                    .contentType(MediaType.valueOf("video/mp4; charset=UTF-8"))
                    .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(size))
                    .body(null);
        }



        long start = 0;
        String[] split = range.substring(6).split("-");
        if (split.length > 0) {
            // 暂时不做校验
            start = Long.valueOf(split[0]);
        }

        long end = 0;
        if (size - start > videoConfig.getChunkSize() * videoConfig.getChunkUnit()) {
            end = start + videoConfig.getChunkSize() * videoConfig.getChunkUnit() - 1;
        } else {
            end = size - 1;
        }


        // 得提前对视频切片，这种方式 越往后由于skip，chunk返回的越慢
        PartialFileResource partialFileResource = new PartialFileResource(file, start, end);
        //FileSystemResource fileSystemResource = new FileSystemResource(file + chunkNum);
        //InputStreamResource inputStreamResource = chunkService.getChunkResource();

        // 返回部分资源
        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .contentType(MediaType.valueOf("video/mp4; charset=UTF-8"))
                // 切片大小
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(partialFileResource.contentLength()))
                // bytes 切片起始偏移-切片结束偏移/资源总大小
                .header(HttpHeaders.CONTENT_RANGE, String.format("bytes %s-%s/%s", start, end, size))
                .header(HttpHeaders.CONTENT_DISPOSITION,"inline; filename=\"4jPEHXdG_9209150941_uhd.mp4\"")
                //.header(HttpHeaders.CACHE_CONTROL,"max-age=2592000")
                //.header(HttpHeaders.AGE,"1213706")
                .header("Timing-Allow-Origin","*")
                .body(partialFileResource);

    }

}
