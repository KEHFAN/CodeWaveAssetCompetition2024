package com.netease.lowcode.extension.video.spring.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.netease.lowcode.extension.video.spring.config.VideoConfig;
import com.netease.lowcode.extension.video.spring.io.PartialFileResource;
import com.netease.lowcode.extension.video.spring.model.Video;
import com.netease.lowcode.extension.video.spring.model.VideoInfo;
import com.netease.lowcode.extension.video.spring.service.VideoService;
import com.netease.lowcode.extension.video.spring.utils.StringGenerator;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

@RestController
public class VideoController {

    @Autowired
    private VideoConfig videoConfig;
    @Autowired
    private VideoService videoService;


    @GetMapping("/rest/video/slice")
    public ResponseEntity<String> sliceVideo(HttpServletRequest request) throws IOException {

        videoService.initDir();

        String key = videoService.sliceVideo();

        return ResponseEntity.ok()
                .body(
                        request.getScheme() + "://" +
                                request.getServerName() + ":" + request.getServerPort() +
                                "/rest/video/get/" + key);
    }

    @CrossOrigin(allowCredentials = "true",methods = {RequestMethod.GET},origins = "*")
    @GetMapping("/rest/video/get/{key}")
    public ResponseEntity<Resource> getVideo(HttpServletResponse httpServletResponse, @RequestHeader(value = "Range", required = false) String range,
                                             @PathVariable String key) throws IOException {

        Video video = null;
        try {
            videoService.initDir();

            // 读取视频配置
            VideoInfo videoInfo = videoService.getVideoInfo(key);
            long size = videoInfo.getFileSize();

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
            if (size - start > videoInfo.getChunkSize() * videoInfo.getChunkUnit()) {
                end = start + videoInfo.getChunkSize() * videoInfo.getChunkUnit() - 1;
            } else {
                end = size - 1;
            }

            video = videoService.getVideo(key, start, end);

            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                    .contentType(MediaType.valueOf("video/mp4; charset=UTF-8"))
                    // 切片大小
                    .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(video.getResource().contentLength()))
                    // bytes 切片起始偏移-切片结束偏移/资源总大小
                    .header(HttpHeaders.CONTENT_RANGE, String.format("bytes %s-%s/%s", start, video.getEnd(), size))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + key + "\"")
                    //.header(HttpHeaders.CACHE_CONTROL,"max-age=2592000")
                    //.header(HttpHeaders.AGE,"1213706")
                    .header("Timing-Allow-Origin", "*")
                    .body(video.getResource());
        } finally {

        }
    }

    @CrossOrigin(allowCredentials = "true",methods = {RequestMethod.GET},origins = "*")
    @GetMapping("/rest/video/get0/{key}")
    public ResponseEntity<Resource> getVideo0(@RequestHeader(value = "Range", required = false) String range,
                                             @PathVariable String key) throws IOException {

        videoService.initDir();

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
