package com.netease.lowcode.extension.video.spring.model;

import java.util.List;

public class VideoInfo {

    private Long fileSize;
    private int chunkUnit;
    private Long chunkSize;
    private List<Long> slice;

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public List<Long> getSlice() {
        return slice;
    }

    public void setSlice(List<Long> slice) {
        this.slice = slice;
    }

    public int getChunkUnit() {
        return chunkUnit;
    }

    public void setChunkUnit(int chunkUnit) {
        this.chunkUnit = chunkUnit;
    }

    public Long getChunkSize() {
        return chunkSize;
    }

    public void setChunkSize(Long chunkSize) {
        this.chunkSize = chunkSize;
    }
}
