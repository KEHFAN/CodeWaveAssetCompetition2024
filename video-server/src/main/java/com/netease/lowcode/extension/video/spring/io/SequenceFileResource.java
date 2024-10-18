package com.netease.lowcode.extension.video.spring.io;

import org.springframework.core.io.FileSystemResource;

import java.io.IOException;
import java.io.InputStream;

public class SequenceFileResource extends FileSystemResource {
    private long start;
    private long end;
    private String[] paths;

    public SequenceFileResource(String[] paths, long start, long end) {
        super(paths[0]);
        this.start = start;
        this.end = end;
        this.paths = paths;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        InputStream inputStream = super.getInputStream();
        return new SequenceInputStream(this.paths,inputStream, start, end);
    }

    @Override
    public long contentLength() throws IOException {
        return end - start + 1;
    }
}
