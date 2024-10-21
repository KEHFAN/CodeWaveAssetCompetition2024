package com.netease.lowcode.extension.video.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses={VideoAutoScan.class})
public class VideoAutoScan {
}
