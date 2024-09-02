package com.netease.lowcode.extension.jmx;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.List;

public class Test {

    public static void main(String[] args) {
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        operatingSystemMXBean.getName();
        operatingSystemMXBean.getArch();
        operatingSystemMXBean.getVersion();
        operatingSystemMXBean.getAvailableProcessors();

        List<GarbageCollectorMXBean> garbageCollectorMXBeans = ManagementFactory.getGarbageCollectorMXBeans();

        ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
        ManagementFactory.getClassLoadingMXBean();
        ManagementFactory.getThreadMXBean();
    }
}
