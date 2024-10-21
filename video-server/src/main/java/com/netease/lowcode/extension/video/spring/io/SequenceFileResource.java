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
        // 这里的skip是按照一个大文件来考虑的，由于每次Range不通，因此为了避免重复传输，需要跳过已传输的部分
        // 但是对于已经分片的文件，这里的start就不能基于完整文件，这样一个分片可能很小，直接跳过skip很可能超出分片长度，导致没有数据
        // 因此这里需要根据start 、 分片的大小，来修改需要跳过的值，因为也可能从一个分片的中间开始读取数据
        // 为了简单起见，约定，只允许start 未分片的中间，end如果超过了分片的范围，将重置end为分片的末尾offset，而不进行跨分片操作。
        // skip还是需要的。
        inputStream.skip(start);
        return new SequenceInputStream(this.paths,inputStream, start, end);
    }

    @Override
    public long contentLength() throws IOException {
        return end - start + 1;
    }
}
