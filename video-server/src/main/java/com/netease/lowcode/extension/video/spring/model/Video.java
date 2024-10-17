package com.netease.lowcode.extension.video.spring.model;

import com.netease.lowcode.extension.video.spring.io.PartialFileResource;

public class Video {

    private long start;
    private long end;
    private long size;
    private PartialFileResource resource;

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public PartialFileResource getResource() {
        return resource;
    }

    public void setResource(PartialFileResource resource) {
        this.resource = resource;
    }
}
