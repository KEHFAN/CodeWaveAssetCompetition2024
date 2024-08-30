package com.netease.lowcode.extension.logs.parse;

import java.util.regex.Pattern;

public enum LogMatchEnum {

    STACK_1(".*\\[handleException].*", ""),
    STACK_2(".*\\..*Exception:.*", ""),
    STACK_3(".*at com\\..*\\..*\\.service\\.logics\\..*CustomizeService\\..*\\(.*CustomizeService\\.java:.*",""),
    STACK_4(".*at com\\..*\\..*\\.web\\.controller\\.logics\\..*CustomizeController\\..*\\(.*CustomizeController\\.java:.*",""),
    STACK_5(".*at com\\..*\\..*\\.web\\.controller\\.rest\\..*Controller\\..*\\(.*Controller\\.java:.*",""),
    STACK_6(".*job执行异常.*",""),
    STACK_7(".*at com\\..*\\..*\\.timing\\..*CustomizeJob\\.execute\\(.*CustomizeJob\\.java:.*",""),
    ;

    private String pattern;
    private String desc;

    LogMatchEnum(String pattern, String desc) {
        this.pattern = pattern;
        this.desc = desc;
    }

    public boolean match(String row) {
        return Pattern.matches(this.pattern, row);
    }

}
