package com.netease.lowcode.extension.spring.controller;

import com.netease.lowcode.extension.config.DevToolsConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnalyzeController {

    /**
     * 获取统计数据
     *
     * @return
     */
    @GetMapping("/rest/analyze/data")
    public Object data() {
        return DevToolsConfig.aopAnalyzeData;
    }

    /**
     * 统计指定时间内的方法耗时排名
     *
     * @param time 统计时间 单位毫秒
     * @return
     */
    @GetMapping("/rest/analyze/start")
    public Object start(Long time) {
        DevToolsConfig.aopAnalyzeData.clear();
        DevToolsConfig.aopAnalyzeStartTime.set(0);
        DevToolsConfig.aopAnalyzeTime.set(time);
        DevToolsConfig.aopSwitch.set(true);
        return "开始统计...";
    }

}
