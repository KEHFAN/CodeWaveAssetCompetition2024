package com.netease.lowcode.extension.spring.component;

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

        String declaringTypeName = joinPoint.getSignature().getDeclaringTypeName();
        String name = joinPoint.getSignature().getName();


        System.out.println("拦截方法："+declaringTypeName + " ### " + name);
        try {
            return joinPoint.proceed();
        }catch (Throwable e){
            throw new RuntimeException("");
        }
    }
}
