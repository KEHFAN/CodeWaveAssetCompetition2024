package com.netease.lowcode.extension.logs.parse;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Pattern;

/**
 * parse logs/biz/origin/native.log
 */
public class NativeLogParse {

    /**
     * 直接复制日志内容解析
     *
     * @param log
     */
    public static void parseString(String log, Consumer<String> consumer) {
        if(StringUtils.isBlank(log)){
            // 日志文件为空
            return;
        }

        // 传入的日志格式最好不要修改
        // 按照 换行切分
        String[] split = StringUtils.split(log, "\n");

        List<String> rowList = Arrays.asList(split);
        for (String row : rowList) {

            // java.lang.ArithmeticException: Division by zero
            if(row.contains("[handleException]")){
                // 解析时间 traceId 以及执行错误
                System.out.println(row);
            }

            if (Pattern.matches(".*\\..*Exception:.*", row)) {

                // 解析异常类型，以及异常msg
                System.out.println(row);
            }

            // at com.cstest.logs.service.logics.Logic1CustomizeService.logic1(Logic1CustomizeService.java:15) ~[classes/:?]
            if (Pattern.matches(".*at com\\..*\\..*\\.service\\.logics\\..*CustomizeService\\..*\\(.*CustomizeService\\.java:.*", row)) {

                // 解析报错的逻辑
                System.out.println(row);
            }

            // at com.cstest.logs.web.controller.logics.Logic1CustomizeController.logic1(Logic1CustomizeController.java:18) ~[classes/:?]
            if (Pattern.matches(".*at com\\..*\\..*\\.web\\.controller\\.logics\\..*CustomizeController\\..*\\(.*CustomizeController\\.java:.*", row)) {

                // 解析报错逻辑
                System.out.println(row);
            }

            // at com.defaulttenant.v306sms.web.controller.rest.GetGoodsInfoController.getGoodsInfo(GetGoodsInfoController.java:45) ~[classes!/:1.0-SNAPSHOT]
            if (Pattern.matches(".*at com\\..*\\..*\\.web\\.controller\\.rest\\..*Controller\\..*\\(.*Controller\\.java:.*", row)) {
                System.out.println(row);
            }
        }

        // 根据第一个报错，启动arthas stack
        System.out.println();

    }

    /**
     * 直接传入日志文件解析
     *
     * @param logFile
     */
    public static void parseFile(File logFile){

    }

}
