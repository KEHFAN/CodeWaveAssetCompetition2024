package logs.parse;

import com.netease.lowcode.extension.logs.parse.NativeLogParse;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

public class NativeLogParseTest {

    @Test
    public void parseStringTest() {

        NativeLogParse.parseString(str, new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });
    }

    String str4 = "[2024-07-25T14:13:21.017] [INFO] [] [N1A050E0_Worker-17] [com.kehfan.devtools.timing.TaskCustomizeJob:29] [execute] job执行异常\n" +
            "java.lang.ArithmeticException: Division by zero\n" +
            "\tat java.math.BigDecimal.divide(BigDecimal.java:1745) ~[?:1.8.0_322]\n" +
            "\tat com.kehfan.devtools.service.logics.TaskCustomizeService.task(TaskCustomizeService.java:22) ~[classes/:?]\n" +
            "\tat com.kehfan.devtools.service.logics.TaskCustomizeService$$FastClassBySpringCGLIB$$f6fddacb.invoke(<generated>) ~[classes/:?]\n" +
            "\tat org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218) ~[spring-core-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "\tat org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:771) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "\tat org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "\tat org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:749) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "\tat org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:88) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "\tat com.kehfan.devtools.web.interceptor.ServiceLoggingAspect.logAround(ServiceLoggingAspect.java:60) ~[classes/:?]\n" +
            "\tat sun.reflect.GeneratedMethodAccessor83.invoke(Unknown Source) ~[?:?]\n" +
            "\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[?:1.8.0_322]\n" +
            "\tat java.lang.reflect.Method.invoke(Method.java:498) ~[?:1.8.0_322]\n" +
            "\tat org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:644) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "\tat org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:633) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "\tat org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:70) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "\tat org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "\tat org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:749) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "\tat org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:95) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "\tat org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "\tat org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:749) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "\tat org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:691) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "\tat com.kehfan.devtools.service.logics.TaskCustomizeService$$EnhancerBySpringCGLIB$$93753aec.task(<generated>) ~[classes/:?]\n" +
            "\tat com.kehfan.devtools.timing.TaskCustomizeJob.execute(TaskCustomizeJob.java:27) ~[classes/:?]\n" +
            "\tat org.quartz.core.JobRunShell.run(JobRunShell.java:202) ~[quartz-2.3.2.jar:?]\n" +
            "\tat org.quartz.simpl.SimpleThreadPool$WorkerThread.run(SimpleThreadPool.java:573) ~[quartz-2.3.2.jar:?]";

    String str3 = "org.quartz.JobExecutionException: null\n" +
            "\tat com.defaulttenant.saastrade.timing.MarketingManagementChangeStatusCustomizeJob.execute(MarketingManagementChangeStatusCustomizeJob.java:58) ~[classes!/:1.0-SNAPSHOT]\n" +
            "\tat org.quartz.core.JobRunShell.run(JobRunShell.java:202) ~[quartz-2.3.2.jar!/:?]\n" +
            "\tat org.quartz.simpl.SimpleThreadPool$WorkerThread.run(SimpleThreadPool.java:573) ~[quartz-2.3.2.jar!/:?]";

    String str2 = "\tat org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:868) ~[tomcat-embed-core-9.0.37.jar!/:9.0.37]\n" +
            "\tat org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1589) ~[tomcat-embed-core-9.0.37.jar!/:9.0.37]\n" +
            "\tat org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49) ~[tomcat-embed-core-9.0.37.jar!/:9.0.37]\n" +
            "\tat java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149) ~[?:1.8.0_342]\n" +
            "\tat java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624) ~[?:1.8.0_342]\n" +
            "\tat org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61) ~[tomcat-embed-core-9.0.37.jar!/:9.0.37]\n" +
            "\tat java.lang.Thread.run(Thread.java:750) ~[?:1.8.0_342]\n" +
            "[2024-07-17T17:23:31.415] [ERROR] [241887f9-3c79-42a2-a7e7-f3475ca7dda6] [http-nio-5004-exec-5] [com.defaulttenant.v306sms.iam.auth.NaslSystemExtendAuthService:45] [getSession] NaslSystemExtendAuthService parse userInfo error = null\n" +
            "[2024-07-17T17:23:37.712] [ERROR] [fe28cf86-e229-4191-b33b-37f57a6f38d5] [http-nio-5004-exec-4] [com.defaulttenant.v306sms.iam.auth.NaslSystemExtendAuthService:45] [getSession] NaslSystemExtendAuthService parse userInfo error = null\n" +
            "[2024-07-17T17:23:37.712] [ERROR] [fe28cf86-e229-4191-b33b-37f57a6f38d5] [http-nio-5004-exec-4] [com.defaulttenant.v306sms.web.interceptor.GlobalExceptionHandler:96] [handleException] 执行错误\n" +
            "java.lang.NullPointerException: null\n" +
            "\tat com.defaulttenant.v306sms.service.logics.GetGoodsInfoCustomizeService.equals(GetGoodsInfoCustomizeService.java:58) ~[classes!/:1.0-SNAPSHOT]\n" +
            "\tat com.defaulttenant.v306sms.service.logics.GetGoodsInfoCustomizeService.getGoodsInfo(GetGoodsInfoCustomizeService.java:108) ~[classes!/:1.0-SNAPSHOT]\n" +
            "\tat com.defaulttenant.v306sms.service.logics.GetGoodsInfoCustomizeService$$FastClassBySpringCGLIB$$6b536d38.invoke(<generated>) ~[classes!/:1.0-SNAPSHOT]\n" +
            "\tat org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218) ~[spring-core-5.2.8.RELEASE.jar!/:5.2.8.RELEASE]\n" +
            "\tat org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:771) ~[spring-aop-5.2.8.RELEASE.jar!/:5.2.8.RELEASE]\n" +
            "\tat org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163) ~[spring-aop-5.2.8.RELEASE.jar!/:5.2.8.RELEASE]\n" +
            "\tat org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:749) ~[spring-aop-5.2.8.RELEASE.jar!/:5.2.8.RELEASE]\n" +
            "\tat org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:88) ~[spring-aop-5.2.8.RELEASE.jar!/:5.2.8.RELEASE]\n" +
            "\tat com.defaulttenant.v306sms.web.interceptor.ServiceLoggingAspect.logAround(ServiceLoggingAspect.java:60) ~[classes!/:1.0-SNAPSHOT]\n" +
            "\tat sun.reflect.GeneratedMethodAccessor125.invoke(Unknown Source) ~[?:?]\n" +
            "\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[?:1.8.0_342]\n" +
            "\tat java.lang.reflect.Method.invoke(Method.java:498) ~[?:1.8.0_342]\n" +
            "\tat org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:644) ~[spring-aop-5.2.8.RELEASE.jar!/:5.2.8.RELEASE]\n" +
            "\tat org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:633) ~[spring-aop-5.2.8.RELEASE.jar!/:5.2.8.RELEASE]\n" +
            "\tat org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:70) ~[spring-aop-5.2.8.RELEASE.jar!/:5.2.8.RELEASE]\n" +
            "\tat org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186) ~[spring-aop-5.2.8.RELEASE.jar!/:5.2.8.RELEASE]\n" +
            "\tat org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:749) ~[spring-aop-5.2.8.RELEASE.jar!/:5.2.8.RELEASE]\n" +
            "\tat org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:95) ~[spring-aop-5.2.8.RELEASE.jar!/:5.2.8.RELEASE]\n" +
            "\tat org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186) ~[spring-aop-5.2.8.RELEASE.jar!/:5.2.8.RELEASE]\n" +
            "\tat org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:749) ~[spring-aop-5.2.8.RELEASE.jar!/:5.2.8.RELEASE]\n" +
            "\tat org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:691) ~[spring-aop-5.2.8.RELEASE.jar!/:5.2.8.RELEASE]\n" +
            "\tat com.defaulttenant.v306sms.service.logics.GetGoodsInfoCustomizeService$$EnhancerBySpringCGLIB$$918737de.getGoodsInfo(<generated>) ~[classes!/:1.0-SNAPSHOT]\n" +
            "\tat com.defaulttenant.v306sms.web.controller.rest.GetGoodsInfoController.getGoodsInfo(GetGoodsInfoController.java:45) ~[classes!/:1.0-SNAPSHOT]\n" +
            "\tat sun.reflect.GeneratedMethodAccessor347.invoke(Unknown Source) ~[?:?]\n" +
            "\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[?:1.8.0_342]\n" +
            "\tat java.lang.reflect.Method.invoke(Method.java:498) ~[?:1.8.0_342]\n" +
            "\tat org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:190) ~[spring-web-5.2.8.RELEASE.jar!/:5.2.8.RELEASE]\n" +
            "\tat org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:138) ~[spring-web-5.2.8.RELEASE.jar!/:5.2.8.RELEASE]\n" +
            "\tat org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:105) ~[spring-webmvc-5.2.8.RELEASE.jar!/:5.2.8.RELEASE]\n" +
            "\tat org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:878) ~[spring-webmvc-5.2.8.RELEASE.jar!/:5.2.8.RELEASE]\n" +
            "\tat org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:792) ~[spring-webmvc-5.2.8.RELEASE.jar!/:5.2.8.RELEASE]\n" +
            "\tat org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87) ~[spring-webmvc-5.2.8.RELEASE.jar!/:5.2.8.RELEASE]\n" +
            "\tat org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1040) ~[spring-webmvc-5.2.8.RELEASE.jar!/:5.2.8.RELEASE]\n" +
            "\tat org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:943) ~[spring-webmvc-5.2.8.RELEASE.jar!/:5.2.8.RELEASE]\n" +
            "\tat org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1006) ~[spring-webmvc-5.2.8.RELEASE.jar!/:5.2.8.RELEASE]\n" +
            "\tat org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:909) ~[spring-webmvc-5.2.8.RELEASE.jar!/:5.2.8.RELEASE]\n" +
            "\tat javax.servlet.http.HttpServlet.service(HttpServlet.java:652) ~[tomcat-embed-core-9.0.37.jar!/:4.0.FR]\n" +
            "\tat org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:883) ~[spring-webmvc-5.2.8.RELEASE.jar!/:5.2.8.RELEASE]\n" +
            "\tat javax.servlet.http.HttpServlet.service(HttpServlet.java:733) ~[tomcat-embed-core-9.0.37.jar!/:4.0.FR]\n" +
            "\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:231) ~[tomcat-embed-core-9.0.37.jar!/:9.0.37]\n" +
            "\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-9.0.37.jar!/:9.0.37]\n" +
            "\tat org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:53) ~[tomcat-embed-websocket-9.0.37.jar!/:9.0.37]\n" +
            "\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) ~[tomcat-embed-core-9.0.37.jar!/:9.0.37]\n" +
            "\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-9.0.37.jar!/:9.0.37]\n" +
            "\tat com.defaulttenant.v306sms.web.interceptor.BasePathFilter.doFilter(BasePathFilter.java:29) ~[classes!/:1.0-SNAPSHOT]\n" +
            "\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) ~[tomcat-embed-core-9.0.37.jar!/:9.0.37]\n" +
            "\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-9.0.37.jar!/:9.0.37]\n" +
            "\tat org.springframework.web.filter.CorsFilter.doFilterInternal(CorsFilter.java:92) ~[spring-web-5.2.8.RELEASE.jar!/:5.2.8.RELEASE]\n" +
            "\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119) ~[spring-web-5.2.8.RELEASE.jar!/:5.2.8.RELEASE]\n" +
            "\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) ~[tomcat-embed-core-9.0.37.jar!/:9.0.37]\n" +
            "\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-9.0.37.jar!/:9.0.37]\n" +
            "\tat com.defaulttenant.v306sms.web.interceptor.UserContextFilter.doFilter(UserContextFilter.java:70) ~[classes!/:1.0-SNAPSHOT]\n" +
            "\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) ~[tomcat-embed-core-9.0.37.jar!/:9.0.37]\n" +
            "\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-9.0.37.jar!/:9.0.37]\n" +
            "\tat com.defaulttenant.v306sms.web.interceptor.ForwardFilter.doFilter(ForwardFilter.java:34) ~[classes!/:1.0-SNAPSHOT]\n" +
            "\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) ~[tomcat-embed-core-9.0.37.jar!/:9.0.37]\n" +
            "\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-9.0.37.jar!/:9.0.37]\n" +
            "\tat org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100) ~[spring-web-5.2.8.RELEASE.jar!/:5.2.8.RELEASE]\n" +
            "\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119) ~[spring-web-5.2.8.RELEASE.jar!/:5.2.8.RELEASE]\n" +
            "\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) ~[tomcat-embed-core-9.0.37.jar!/:9.0.37]\n" +
            "\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-9.0.37.jar!/:9.0.37]\n" +
            "\tat org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93) ~[spring-web-5.2.8.RELEASE.jar!/:5.2.8.RELEASE]\n" +
            "\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119) ~[spring-web-5.2.8.RELEASE.jar!/:5.2.8.RELEASE]\n" +
            "\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) ~[tomcat-embed-core-9.0.37.jar!/:9.0.37]\n" +
            "\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-9.0.37.jar!/:9.0.37]\n" +
            "\tat org.springframework.boot.actuate.metrics.web.servlet.WebMvcMetricsFilter.doFilterInternal(WebMvcMetricsFilter.java:109) ~[spring-boot-actuator-2.2.9.RELEASE.jar!/:2.2.9.RELEASE]\n" +
            "\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119) ~[spring-web-5.2.8.RELEASE.jar!/:5.2.8.RELEASE]\n" +
            "\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) ~[tomcat-embed-core-9.0.37.jar!/:9.0.37]\n" +
            "\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-9.0.37.jar!/:9.0.37]\n" +
            "\tat org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201) ~[spring-web-5.2.8.RELEASE.jar!/:5.2.8.RELEASE]\n" +
            "\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119) ~[spring-web-5.2.8.RELEASE.jar!/:5.2.8.RELEASE]\n" +
            "\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) ~[tomcat-embed-core-9.0.37.jar!/:9.0.37]\n" +
            "\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-9.0.37.jar!/:9.0.37]\n" +
            "\tat org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:202) ~[tomcat-embed-core-9.0.37.jar!/:9.0.37]\n" +
            "\tat org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:96) ~[tomcat-embed-core-9.0.37.jar!/:9.0.37]\n" +
            "\tat org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:541) ~[tomcat-embed-core-9.0.37.jar!/:9.0.37]\n" +
            "\tat org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:139) ~[tomcat-embed-core-9.0.37.jar!/:9.0.37]\n" +
            "\tat org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:92) ~[tomcat-embed-core-9.0.37.jar!/:9.0.37]\n" +
            "\tat org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74) ~[tomcat-embed-core-9.0.37.jar!/:9.0.37]\n" +
            "\tat org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:343) ~[tomcat-embed-core-9.0.37.jar!/:9.0.37]\n" +
            "\tat org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:373) ~[tomcat-embed-core-9.0.37.jar!/:9.0.37]\n" +
            "\tat org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:65) ~[tomcat-embed-core-9.0.37.jar!/:9.0.37]\n" +
            "\tat org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:868) ~[tomcat-embed-core-9.0.37.jar!/:9.0.37]\n" +
            "\tat org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1589) ~[tomcat-embed-core-9.0.37.jar!/:9.0.37]\n" +
            "\tat org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49) ~[tomcat-embed-core-9.0.37.jar!/:9.0.37]\n" +
            "\tat java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149) ~[?:1.8.0_342]\n" +
            "\tat java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624) ~[?:1.8.0_342]\n" +
            "\tat org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61) ~[tomcat-embed-core-9.0.37.jar!/:9.0.37]\n" +
            "\tat java.lang.Thread.run(Thread.java:750) ~[?:1.8.0_342]\n";

    String str = "[2024-07-18T11:22:05.939] [ERROR] [4a06c78e-922e-4015-9f6b-7d1ece578ef6] [http-nio-8080-exec-6] [com.cstest.logs.web.interceptor.GlobalExceptionHandler:98] [handleException] 执行错误\n" +
            "java.lang.ArithmeticException: Division by zero\n" +
            "        at java.math.BigDecimal.divide(BigDecimal.java:1745) ~[?:1.8.0_322]\n" +
            "        at com.cstest.logs.service.logics.Logic1CustomizeService.logic1(Logic1CustomizeService.java:15) ~[classes/:?]\n" +
            "        at com.cstest.logs.service.logics.Logic1CustomizeService$$FastClassBySpringCGLIB$$e169dfc5.invoke(<generated>) ~[classes/:?]\n" +
            "        at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218) ~[spring-core-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:771) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:749) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:88) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at com.cstest.logs.web.interceptor.ServiceLoggingAspect.logAround(ServiceLoggingAspect.java:60) ~[classes/:?]\n" +
            "        at sun.reflect.GeneratedMethodAccessor89.invoke(Unknown Source) ~[?:?]\n" +
            "        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[?:1.8.0_322]\n" +
            "        at java.lang.reflect.Method.invoke(Method.java:498) ~[?:1.8.0_322]\n" +
            "        at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:644) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:633) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:70) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:749) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:95) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:749) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:691) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at com.cstest.logs.service.logics.Logic1CustomizeService$$EnhancerBySpringCGLIB$$44055f6a.logic1(<generated>) ~[classes/:?]\n" +
            "        at com.cstest.logs.service.logics.Logic1CustomizeService$HOTSWAPAGENT_$$FastClassBySpringCGLIB$$e169dfc5.invoke(<generated>) ~[classes/:?]\n" +
            "        at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218) ~[spring-core-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at HotswapSpringCallback_1796692748.intercept(HotswapSpringCallback_1796692748.java) ~[classes/:?]\n" +
            "        at com.cstest.logs.service.logics.Logic1CustomizeService$HOTSWAPAGENT_$$EnhancerBySpringCGLIB$$f50f5580.logic1(<generated>) ~[classes/:?]\n" +
            "        at com.cstest.logs.service.logics.Logic2CustomizeService.logic2(Logic2CustomizeService.java:16) ~[extraClasspath/:?]\n" +
            "        at com.cstest.logs.service.logics.Logic2CustomizeService$$FastClassBySpringCGLIB$$3213bdc6.invoke(<generated>) ~[extraClasspath/:?]\n" +
            "        at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218) ~[spring-core-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:771) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:749) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:88) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at com.cstest.logs.web.interceptor.ServiceLoggingAspect.logAround(ServiceLoggingAspect.java:60) ~[classes/:?]\n" +
            "        at sun.reflect.GeneratedMethodAccessor89.invoke(Unknown Source) ~[?:?]\n" +
            "        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[?:1.8.0_322]\n" +
            "        at java.lang.reflect.Method.invoke(Method.java:498) ~[?:1.8.0_322]\n" +
            "        at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:644) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:633) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:70) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:749) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:95) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:749) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:691) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at com.cstest.logs.service.logics.Logic2CustomizeService$$EnhancerBySpringCGLIB$$5bb9420d.logic2(<generated>) ~[extraClasspath/:?]\n" +
            "        at com.cstest.logs.service.logics.Logic2CustomizeService$HOTSWAPAGENT_$$FastClassBySpringCGLIB$$3213bdc6.invoke(<generated>) ~[extraClasspath/:?]\n" +
            "        at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218) ~[spring-core-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at HotswapSpringCallback_1796692748.intercept(HotswapSpringCallback_1796692748.java) ~[classes/:?]\n" +
            "        at com.cstest.logs.service.logics.Logic2CustomizeService$HOTSWAPAGENT_$$EnhancerBySpringCGLIB$$deadfdf3.logic2(<generated>) ~[extraClasspath/:?]\n" +
            "        at com.cstest.logs.service.logics.Logic3CustomizeService.logic3(Logic3CustomizeService.java:16) ~[extraClasspath/:?]\n" +
            "        at com.cstest.logs.service.logics.Logic3CustomizeService$$FastClassBySpringCGLIB$$82bd9bc7.invoke(<generated>) ~[extraClasspath/:?]\n" +
            "        at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218) ~[spring-core-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:771) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:749) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:88) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at com.cstest.logs.web.interceptor.ServiceLoggingAspect.logAround(ServiceLoggingAspect.java:60) ~[classes/:?]\n" +
            "        at sun.reflect.GeneratedMethodAccessor89.invoke(Unknown Source) ~[?:?]\n" +
            "        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[?:1.8.0_322]\n" +
            "        at java.lang.reflect.Method.invoke(Method.java:498) ~[?:1.8.0_322]\n" +
            "        at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:644) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:633) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:70) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:749) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:95) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:749) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:691) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at com.cstest.logs.service.logics.Logic3CustomizeService$$EnhancerBySpringCGLIB$$736d24b0.logic3(<generated>) ~[extraClasspath/:?]\n" +
            "        at com.cstest.logs.service.logics.Logic3CustomizeService$HOTSWAPAGENT_$$FastClassBySpringCGLIB$$82bd9bc7.invoke(<generated>) ~[extraClasspath/:?]\n" +
            "        at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218) ~[spring-core-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at HotswapSpringCallback_1796692748.intercept(HotswapSpringCallback_1796692748.java) ~[classes/:?]\n" +
            "        at com.cstest.logs.service.logics.Logic3CustomizeService$HOTSWAPAGENT_$$EnhancerBySpringCGLIB$$c84ca666.logic3(<generated>) ~[extraClasspath/:?]\n" +
            "        at com.cstest.logs.service.logics.Logic4CustomizeService.logic4(Logic4CustomizeService.java:16) ~[extraClasspath/:?]\n" +
            "        at com.cstest.logs.service.logics.Logic4CustomizeService$$FastClassBySpringCGLIB$$d36779c8.invoke(<generated>) ~[extraClasspath/:?]\n" +
            "        at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218) ~[spring-core-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:771) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:749) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:88) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at com.cstest.logs.web.interceptor.ServiceLoggingAspect.logAround(ServiceLoggingAspect.java:60) ~[classes/:?]\n" +
            "        at sun.reflect.GeneratedMethodAccessor89.invoke(Unknown Source) ~[?:?]\n" +
            "        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[?:1.8.0_322]\n" +
            "        at java.lang.reflect.Method.invoke(Method.java:498) ~[?:1.8.0_322]\n" +
            "        at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:644) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:633) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:70) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:749) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:95) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:749) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:691) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at com.cstest.logs.service.logics.Logic4CustomizeService$$EnhancerBySpringCGLIB$$8b210753.logic4(<generated>) ~[extraClasspath/:?]\n" +
            "        at com.cstest.logs.service.logics.Logic4CustomizeService$HOTSWAPAGENT_$$FastClassBySpringCGLIB$$d36779c8.invoke(<generated>) ~[extraClasspath/:?]\n" +
            "        at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218) ~[spring-core-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at HotswapSpringCallback_1796692748.intercept(HotswapSpringCallback_1796692748.java) ~[classes/:?]\n" +
            "        at com.cstest.logs.service.logics.Logic4CustomizeService$HOTSWAPAGENT_$$EnhancerBySpringCGLIB$$b1eb4ed9.logic4(<generated>) ~[extraClasspath/:?]\n" +
            "        at com.cstest.logs.web.controller.logics.Logic4CustomizeController.logic4(Logic4CustomizeController.java:18) ~[extraClasspath/:?]\n" +
            "        at com.cstest.logs.web.controller.logics.Logic4CustomizeController$$FastClassBySpringCGLIB$$bbc78488.invoke(<generated>) ~[extraClasspath/:?]\n" +
            "        at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218) ~[spring-core-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:771) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:749) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint.proceed(MethodInvocationProceedingJoinPoint.java:88) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at com.cstest.logs.web.interceptor.ValidationAspect.validateAround(ValidationAspect.java:48) ~[classes/:?]\n" +
            "        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[?:1.8.0_322]\n" +
            "        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[?:1.8.0_322]\n" +
            "        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[?:1.8.0_322]\n" +
            "        at java.lang.reflect.Method.invoke(Method.java:498) ~[?:1.8.0_322]\n" +
            "        at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethodWithGivenArgs(AbstractAspectJAdvice.java:644) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.aspectj.AbstractAspectJAdvice.invokeAdviceMethod(AbstractAspectJAdvice.java:633) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.aspectj.AspectJAroundAdvice.invoke(AspectJAroundAdvice.java:70) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:175) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:749) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.interceptor.ExposeInvocationInterceptor.invoke(ExposeInvocationInterceptor.java:95) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:749) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:691) ~[spring-aop-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at com.cstest.logs.web.controller.logics.Logic4CustomizeController$$EnhancerBySpringCGLIB$$483ddf93.logic4(<generated>) ~[extraClasspath/:?]\n" +
            "        at com.cstest.logs.web.controller.logics.Logic4CustomizeController$HOTSWAPAGENT_$$FastClassBySpringCGLIB$$bbc78488.invoke(<generated>) ~[extraClasspath/:?]\n" +
            "        at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218) ~[spring-core-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at HotswapSpringCallback_1796692748.intercept(HotswapSpringCallback_1796692748.java) ~[classes/:?]\n" +
            "        at com.cstest.logs.web.controller.logics.Logic4CustomizeController$HOTSWAPAGENT_$$EnhancerBySpringCGLIB$$bd4ea319.logic4(<generated>) ~[extraClasspath/:?]\n" +
            "        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[?:1.8.0_322]\n" +
            "        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[?:1.8.0_322]\n" +
            "        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[?:1.8.0_322]\n" +
            "        at java.lang.reflect.Method.invoke(Method.java:498) ~[?:1.8.0_322]\n" +
            "        at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:190) ~[spring-web-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:138) ~[spring-web-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:105) ~[spring-webmvc-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:878) ~[spring-webmvc-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:792) ~[spring-webmvc-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87) ~[spring-webmvc-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1040) ~[spring-webmvc-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:943) ~[spring-webmvc-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1006) ~[spring-webmvc-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:909) ~[spring-webmvc-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at javax.servlet.http.HttpServlet.service(HttpServlet.java:652) ~[tomcat-embed-core-9.0.37.jar:4.0.FR]\n" +
            "        at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:883) ~[spring-webmvc-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at javax.servlet.http.HttpServlet.service(HttpServlet.java:733) ~[tomcat-embed-core-9.0.37.jar:4.0.FR]\n" +
            "        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:231) ~[tomcat-embed-core-9.0.37.jar:9.0.37]\n" +
            "        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-9.0.37.jar:9.0.37]\n" +
            "        at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:53) ~[tomcat-embed-websocket-9.0.37.jar:9.0.37]\n" +
            "        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) ~[tomcat-embed-core-9.0.37.jar:9.0.37]\n" +
            "        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-9.0.37.jar:9.0.37]\n" +
            "        at com.netease.devtools.remote.server.DispatcherFilter.doFilter(DispatcherFilter.java:66) ~[app-preview-devtools.jar:?]\n" +
            "        at com.netease.devtools.remote.server.DispatcherFilter.doFilter(DispatcherFilter.java:54) ~[app-preview-devtools.jar:?]\n" +
            "        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) ~[tomcat-embed-core-9.0.37.jar:9.0.37]\n" +
            "        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-9.0.37.jar:9.0.37]\n" +
            "        at com.cstest.logs.web.interceptor.BasePathFilter.doFilter(BasePathFilter.java:30) ~[classes/:?]\n" +
            "        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) ~[tomcat-embed-core-9.0.37.jar:9.0.37]\n" +
            "        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-9.0.37.jar:9.0.37]\n" +
            "        at com.cstest.logs.web.interceptor.GlobalContextFilter.doFilter(GlobalContextFilter.java:45) ~[classes/:?]\n" +
            "        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) ~[tomcat-embed-core-9.0.37.jar:9.0.37]\n" +
            "        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-9.0.37.jar:9.0.37]\n" +
            "        at org.springframework.web.filter.CorsFilter.doFilterInternal(CorsFilter.java:92) ~[spring-web-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119) ~[spring-web-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) ~[tomcat-embed-core-9.0.37.jar:9.0.37]\n" +
            "        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-9.0.37.jar:9.0.37]\n" +
            "        at com.cstest.logs.web.interceptor.LogicAuthFilter.doFilter(LogicAuthFilter.java:72) ~[classes/:?]\n" +
            "        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) ~[tomcat-embed-core-9.0.37.jar:9.0.37]\n" +
            "        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-9.0.37.jar:9.0.37]\n" +
            "        at com.cstest.logs.web.interceptor.AccessLogFilter.doFilterInternal(AccessLogFilter.java:54) ~[classes/:?]\n" +
            "        at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119) ~[spring-web-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) ~[tomcat-embed-core-9.0.37.jar:9.0.37]\n" +
            "        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-9.0.37.jar:9.0.37]\n" +
            "        at com.cstest.logs.web.interceptor.AuthFilter.doFilter(AuthFilter.java:142) ~[classes/:?]\n" +
            "        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) ~[tomcat-embed-core-9.0.37.jar:9.0.37]\n" +
            "        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-9.0.37.jar:9.0.37]\n" +
            "        at com.cstest.logs.web.interceptor.TraceIdFilter.doFilter(TraceIdFilter.java:36) ~[classes/:?]\n" +
            "        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) ~[tomcat-embed-core-9.0.37.jar:9.0.37]\n" +
            "        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-9.0.37.jar:9.0.37]\n" +
            "        at com.cstest.logs.web.interceptor.UserContextFilter.doFilter(UserContextFilter.java:70) ~[classes/:?]\n" +
            "        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) ~[tomcat-embed-core-9.0.37.jar:9.0.37]\n" +
            "        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-9.0.37.jar:9.0.37]\n" +
            "        at org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100) ~[spring-web-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119) ~[spring-web-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) ~[tomcat-embed-core-9.0.37.jar:9.0.37]\n" +
            "        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-9.0.37.jar:9.0.37]\n" +
            "        at org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93) ~[spring-web-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119) ~[spring-web-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) ~[tomcat-embed-core-9.0.37.jar:9.0.37]\n" +
            "        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-9.0.37.jar:9.0.37]\n" +
            "        at org.springframework.boot.actuate.metrics.web.servlet.WebMvcMetricsFilter.doFilterInternal(WebMvcMetricsFilter.java:109) ~[spring-boot-actuator-2.2.9.RELEASE.jar:2.2.9.RELEASE]\n" +
            "        at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119) ~[spring-web-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) ~[tomcat-embed-core-9.0.37.jar:9.0.37]\n" +
            "        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-9.0.37.jar:9.0.37]\n" +
            "        at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201) ~[spring-web-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119) ~[spring-web-5.2.8.RELEASE.jar:5.2.8.RELEASE]\n" +
            "        at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193) ~[tomcat-embed-core-9.0.37.jar:9.0.37]\n" +
            "        at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166) ~[tomcat-embed-core-9.0.37.jar:9.0.37]\n" +
            "        at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:202) ~[tomcat-embed-core-9.0.37.jar:9.0.37]\n" +
            "        at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:96) ~[tomcat-embed-core-9.0.37.jar:9.0.37]\n" +
            "        at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:541) ~[tomcat-embed-core-9.0.37.jar:9.0.37]\n" +
            "        at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:139) ~[tomcat-embed-core-9.0.37.jar:9.0.37]\n" +
            "        at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:92) ~[tomcat-embed-core-9.0.37.jar:9.0.37]\n" +
            "        at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74) ~[tomcat-embed-core-9.0.37.jar:9.0.37]\n" +
            "        at org.apache.catalina.valves.RemoteIpValve.invoke(RemoteIpValve.java:747) ~[tomcat-embed-core-9.0.37.jar:9.0.37]\n" +
            "        at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:343) ~[tomcat-embed-core-9.0.37.jar:9.0.37]\n" +
            "        at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:373) ~[tomcat-embed-core-9.0.37.jar:9.0.37]\n" +
            "        at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:65) ~[tomcat-embed-core-9.0.37.jar:9.0.37]\n" +
            "        at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:868) ~[tomcat-embed-core-9.0.37.jar:9.0.37]\n" +
            "        at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1589) ~[tomcat-embed-core-9.0.37.jar:9.0.37]\n" +
            "        at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49) ~[tomcat-embed-core-9.0.37.jar:9.0.37]\n" +
            "        at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149) ~[?:1.8.0_322]\n" +
            "        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624) ~[?:1.8.0_322]\n" +
            "        at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61) ~[tomcat-embed-core-9.0.37.jar:9.0.37]\n" +
            "        at java.lang.Thread.run(Thread.java:750) ~[?:1.8.0_322]";

}
