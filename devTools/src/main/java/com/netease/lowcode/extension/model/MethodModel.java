package com.netease.lowcode.extension.model;

public class MethodModel {

    /**
     * 唯一标识符：类型###方法名
     * 对于重载方法，暂不考虑拆分，合并计算会导致数据不准
     */
    private String uniqueId;
    private String classType;
    private String methodName;
    /**
     * 可能对应的逻辑名 做映射规则
     */
    private String logicName;
    /**
     * 处理调用链路时，作为全局唯一id
     */
    private String traceId;
    /**
     * 统计周期内调用次数
     */
    private Long count;
    /**
     * 统计周期内总耗时（单位毫秒）
     */
    private Long cost;
    /**
     * 周期内平均耗时 （单位毫秒）
     */
    private Double avgCost;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getLogicName() {
        return logicName;
    }

    public void setLogicName(String logicName) {
        this.logicName = logicName;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getCost() {
        return cost;
    }

    public void setCost(Long cost) {
        this.cost = cost;
    }

    public Double getAvgCost() {
        return avgCost;
    }

    public void setAvgCost(Double avgCost) {
        this.avgCost = avgCost;
    }
}
