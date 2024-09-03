package com.netease.lowcode.extension.spring.component;

import com.netease.lowcode.extension.config.DevToolsConfig;
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

        if (DevToolsConfig.aopSwitch.get()) {
            // 设置初始时间
            DevToolsConfig.aopAnalyzeStartTime.compareAndSet(0,System.currentTimeMillis());
            // 判断时间位于统计区间
            if (System.currentTimeMillis() - DevToolsConfig.aopAnalyzeStartTime.get() < DevToolsConfig.aopAnalyzeTime.get()) {

                String declaringTypeName = joinPoint.getSignature().getDeclaringTypeName();
                String name = joinPoint.getSignature().getName();

                // 此处可能存在并发统计不准确
                String key = String.join("##", declaringTypeName, name);
                DevToolsConfig.aopAnalyzeData.put(key, DevToolsConfig.aopAnalyzeData.getOrDefault(key, 0L) + 1);
            } else {
                // 超过时间关闭统计
                DevToolsConfig.aopSwitch.set(false);
                DevToolsConfig.aopAnalyzeStartTime.set(0);
                DevToolsConfig.aopAnalyzeTime.set(0);
            }

        }
        return joinPoint.proceed();
    }
}
