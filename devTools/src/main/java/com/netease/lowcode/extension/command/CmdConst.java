package com.netease.lowcode.extension.command;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

public class CmdConst {

    // public static final String PREFIX = SystemUtils.IS_OS_WINDOWS ? "cmd /c " : "";
    public static final String PREFIX = SystemUtils.IS_OS_WINDOWS ? "powershell.exe -Command " : "";
    public static final String SEPARATOR = SystemUtils.IS_OS_WINDOWS ? " ; " : "";

    public static final String PWD = "pwd";

    public static String LS(String arg) {
        String cmd = "ls";
        if (StringUtils.isNotBlank(arg)) {
            return cmd + " " + arg;
        }
        return cmd;
    }

    public static String CD(String arg) {
        String cmd = "cd";
        return cmd + " " + arg;
    }

    public static String CAT(String... args) {
        String cmd = SystemUtils.IS_OS_WINDOWS ? "type" : "cat";
        StringBuilder sb = new StringBuilder(cmd);
        for (String arg : args) {
            sb.append(" ").append(arg);
        }
        return sb.toString();
    }

    public static final String JAVA_VERSION = "java -version";
    public static final String JPS = "jps";
}
