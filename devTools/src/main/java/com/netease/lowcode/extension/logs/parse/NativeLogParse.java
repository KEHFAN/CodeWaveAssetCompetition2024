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

        StringBuilder sb = new StringBuilder();
        sb.append("欢迎大家贡献更多异常日志，帮助完善功能\n");

        // 传入的日志格式最好不要修改
        // 按照 换行切分
        String[] split = StringUtils.split(log, "\n");

        List<String> rowList = Arrays.asList(split);
        for (String row : rowList) {

            for (LogMatchEnum value : LogMatchEnum.values()) {
                if (value.match(row)) {
                    sb.append(row).append("\n");
                    break;
                }
            }
        }

        consumer.accept(sb.toString());

        // 根据第一个报错，启动arthas stack
    }

    /**
     * 直接传入日志文件解析
     *
     * @param logFile
     */
    public static void parseFile(File logFile){

    }

}
