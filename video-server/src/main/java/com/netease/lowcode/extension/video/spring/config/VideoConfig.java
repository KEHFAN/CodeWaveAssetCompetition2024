package com.netease.lowcode.extension.video.spring.config;

import com.netease.lowcode.core.EnvironmentType;
import com.netease.lowcode.core.annotation.Environment;
import com.netease.lowcode.core.annotation.NaslConfiguration;
import org.apache.commons.lang3.StringUtils;
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
    public Integer chunkUnit = 2048;
    public Integer randomStringLen = 10;

    /**
     * chunk文件存储根目录
     */
    @Value("${baseDir}")
    @NaslConfiguration(defaultValue = {
            @Environment(type = EnvironmentType.DEV, value = "/data"),
            @Environment(type = EnvironmentType.ONLINE, value = "/data")
    })
    public String baseDir;



    public Long getChunkSize() {
        if (Objects.isNull(chunkSize) || chunkSize < 100L) {
            return 100L;
        }
        return chunkSize;
    }

    public void setChunkSize(Long chunkSize) {
        this.chunkSize = chunkSize;
    }

    public String getBaseDir() {
        if (StringUtils.isBlank(baseDir)) {
            return "/data";
        }
        return baseDir;
    }

    public void setBaseDir(String baseDir) {
        this.baseDir = baseDir;
    }

    public int getChunkUnit() {
        return chunkUnit;
    }

    public void setChunkUnit(Integer chunkUnit) {
        this.chunkUnit = chunkUnit;
    }

    public Integer getRandomStringLen() {
        return randomStringLen;
    }

    public void setRandomStringLen(Integer randomStringLen) {
        this.randomStringLen = randomStringLen;
    }
}
