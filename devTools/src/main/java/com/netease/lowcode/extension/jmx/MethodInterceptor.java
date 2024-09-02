package com.netease.lowcode.extension.jmx;

import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class MethodInterceptor {

    @RuntimeType
    public static Object intercept(@Origin Method method, @SuperCall Callable<?> callable) throws Exception {
        try{
            System.out.println("开始调用方法:"+method.getName());
            return callable.call();
        } finally {
            System.out.println("方法执行结束:"+method.getName());
        }
    }
}
