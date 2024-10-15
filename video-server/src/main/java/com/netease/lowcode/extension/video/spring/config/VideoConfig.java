package com.netease.lowcode.extension.video.spring.config;

import com.netease.lowcode.core.EnvironmentType;
import com.netease.lowcode.core.annotation.Environment;
import com.netease.lowcode.core.annotation.NaslConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class VideoConfig {

    /**
     * 视频分片大小
     */
    @Value("${chunkSize}")
    @NaslConfiguration(defaultValue = {
            @Environment(type = EnvironmentType.DEV, value = ""),
            @Environment(type = EnvironmentType.ONLINE, value = "")
    })
    public Long chunkSize;

    public Long getChunkSize() {
        if (Objects.isNull(chunkSize) || chunkSize < 100L) {
            return 100L;
        }
        return chunkSize;
    }

    public void setChunkSize(Long chunkSize) {
        this.chunkSize = chunkSize;
    }
}
