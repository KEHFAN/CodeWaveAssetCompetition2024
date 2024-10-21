package com.netease.lowcode.extension.video.spring.io;

import org.springframework.core.io.FileSystemResource;

import java.io.IOException;
import java.io.InputStream;

public class SequenceFileResource extends FileSystemResource {

    private long start;
    private long end;
    // 分片起始偏移量
    private long offset;

    public SequenceFileResource(String path, long offset, long start, long end) {
        super(path);
        this.start = start;
        this.end = end;
        this.offset = offset;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        InputStream inputStream = super.getInputStream();
        // 每次返回新的文件流，都会从文件开头开始，因此需要跳过部分字节。
        inputStream.skip(start - offset);
        return new PartialInputStream(inputStream, start - offset, end - offset);
    }

    @Override
    public long contentLength() throws IOException {
        return end - start + 1;
    }
}
