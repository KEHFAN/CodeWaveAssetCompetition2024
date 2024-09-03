package com.netease.lowcode.extension.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class DevToolsConfig {

    /**
     * 是否开启aop拦截应用方法 ，进行统计
     */
    public static final AtomicBoolean aopSwitch = new AtomicBoolean(false);
    /**
     * 开启aop拦截应用方法，一次统计的时长，单位毫秒
     */
    public static final AtomicLong aopAnalyzeTime = new AtomicLong(0);
    /**
     * 开启aop拦截应用方法，一次统计的开始时间
     */
    public static final AtomicLong aopAnalyzeStartTime = new AtomicLong(0);
    /**
     * aop拦截 统计数据
     */
    public static final Map<String,Long> aopAnalyzeData = new ConcurrentHashMap<>();

}
