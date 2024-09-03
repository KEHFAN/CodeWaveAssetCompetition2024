package com.netease.lowcode.extension.spring.controller;

import com.netease.lowcode.extension.config.DevToolsConfig;
import com.netease.lowcode.extension.model.MethodModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class AnalyzeController {

    /**
     * 获取统计数据
     *
     * @return
     */
    @GetMapping("/rest/analyze/data")
    public Object data() {
        return DevToolsConfig.aopAnalyzeDetailData.values()
                .stream()
                .sorted(Comparator.comparingDouble(MethodModel::getAvgCost).reversed())
                .collect(Collectors.toList());
    }

    /**
     * 根据逻辑名查询
     * 这里会统计该逻辑的内部调用路径以及耗时情况
     *
     * @param logicName
     * @return
     */
    public Object queryLogic(String logicName) {
        return null;
    }
    /**
     * 统计指定时间内的方法耗时排名
     *
     * @param time 统计时间 单位毫秒
     * @return
     */
    @GetMapping("/rest/analyze/start")
    public Object start(Long time) {
        DevToolsConfig.aopAnalyzeDetailData.clear();
        DevToolsConfig.aopAnalyzeStartTime.set(0);
        DevToolsConfig.aopAnalyzeTime.set(time);
        DevToolsConfig.aopSwitch.set(true);
        return "开始统计...";
    }

}
