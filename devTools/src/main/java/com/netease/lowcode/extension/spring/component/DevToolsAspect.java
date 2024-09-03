package com.netease.lowcode.extension.spring.component;

import com.netease.lowcode.extension.config.DevToolsConfig;
import com.netease.lowcode.extension.model.MethodModel;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DevToolsAspect {

    @Pointcut("execution(* *(..)) && " +
            "!execution(* io.micrometer..*(..)) && " +
            "!execution(* org.springframework..*(..)) && " +
            "!execution(* java..*(..)) &&" +
            "!execution(* org.apache..*(..)) &&" +
            "!execution(* com.fasterxml..*(..)) &&" +
            "!execution(* io..*(..)) &&" +
            "!execution(* javax..*(..)) &&" +
            "!execution(* com.zaxxer..*(..)) &&" +
            "!execution(* org.mybatis..*(..))")
    public void allMethods(){}

    @Around("allMethods()")
    public Object aroundAllMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        long begin = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        }finally {
            if (DevToolsConfig.aopSwitch.get()) {
                // 设置初始时间
                DevToolsConfig.aopAnalyzeStartTime.compareAndSet(0, System.currentTimeMillis());
                // 判断时间位于统计区间
                if (System.currentTimeMillis() - DevToolsConfig.aopAnalyzeStartTime.get() < DevToolsConfig.aopAnalyzeTime.get()) {

                    String declaringTypeName = joinPoint.getSignature().getDeclaringTypeName();
                    String name = joinPoint.getSignature().getName();

                    // 此处可能存在并发统计不准确
                    // 对于重载方法计算会合并，后续优化考虑拆分
                    String key = String.join("##", declaringTypeName, name);
                    MethodModel model = DevToolsConfig.aopAnalyzeDetailData.getOrDefault(key, initData(key,declaringTypeName,name));
                    model.setCount(model.getCount() + 1);
                    model.setCost(model.getCost() + (System.currentTimeMillis() - begin));
                    model.setAvgCost((double) model.getCost() / model.getCount());
                    DevToolsConfig.aopAnalyzeDetailData.put(key, model);

                } else {
                    // 超过时间关闭统计
                    DevToolsConfig.aopSwitch.set(false);
                    DevToolsConfig.aopAnalyzeStartTime.set(0);
                    DevToolsConfig.aopAnalyzeTime.set(0);
                }

            }
        }
    }

    private MethodModel initData(String key,String typeName,String methodName) {
        MethodModel model = new MethodModel();
        model.setUniqueId(key);
        model.setClassType(typeName);
        model.setMethodName(methodName);
        //model.setLogicName();
        //model.setTraceId();
        model.setCount(0L);
        model.setCost(0L);
        model.setAvgCost(0.0);

        return model;
    }
}
