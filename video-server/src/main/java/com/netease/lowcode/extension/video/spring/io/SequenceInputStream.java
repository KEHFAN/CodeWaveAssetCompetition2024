package com.netease.lowcode.extension.video.spring.io;

import java.io.IOException;
import java.io.InputStream;

public class SequenceInputStream extends InputStream {
    private final InputStream inputStream0;
    private final InputStream inputStream = null;
    private final long start;
    private final long end;
    private long position;
    private String[] paths;

    public SequenceInputStream(String[] paths,InputStream inputStream0, long start, long end) {
        this.inputStream0 = inputStream0;
        this.start = start;
        this.end = end;
        this.paths = paths;
    }

    @Override
    public int read() throws IOException {
        if (position > end) {
            return -1;
        }
        position++;
        return inputStream0.read();
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {

        // 重写逻辑，根据start end判断读取哪个子文件，然后在这里将指定字节内容返回。

        if (position > end) {
            return -1;
        }

        int readLen = (int) Math.min(len, end - position + 1);

        int bytes = 0;
        if (this.start == 0) {
            bytes = inputStream0.read(b, off, readLen);
        }else {
//            this.start
            // 读取其他分片流。
        }
        position += bytes;

        return bytes;
    }

    @Override
    public void close() throws IOException {
        inputStream0.close();
    }
}
