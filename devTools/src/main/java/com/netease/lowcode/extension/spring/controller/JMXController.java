package com.netease.lowcode.extension.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.HashMap;
import java.util.Map;

@RestController
public class JMXController {

    @GetMapping("/rest/jmx/operateSystemInfo")
    public Object getOperateSystemInfo() {
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        operatingSystemMXBean.getName();
        operatingSystemMXBean.getArch();
        operatingSystemMXBean.getVersion();
        operatingSystemMXBean.getAvailableProcessors();

        Map<String,Object> map = new HashMap<>();
        map.put("os.name",operatingSystemMXBean.getName());
        map.put("os.arch",operatingSystemMXBean.getArch());
        map.put("os.version",operatingSystemMXBean.getVersion());
        map.put("os.availableProcessors",operatingSystemMXBean.getAvailableProcessors());
        return map;
    }
}
