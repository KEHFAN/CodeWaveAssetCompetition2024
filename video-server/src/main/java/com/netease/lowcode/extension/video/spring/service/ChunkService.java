package com.netease.lowcode.extension.video.spring.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
public class ChunkService {

    public void chunkVideo(String path) {
        // 对文件进行切片
        // 原始视频生成一个json描述文件，包含文件名称、文件大小、文件chunk信息。
    }

    public InputStreamResource getChunkResource() {
        return new InputStreamResource(new ByteArrayInputStream(new byte[11]));
    }

}
