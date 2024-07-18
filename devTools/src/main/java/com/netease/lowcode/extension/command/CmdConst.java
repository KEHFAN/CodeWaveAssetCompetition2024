package com.netease.lowcode.extension.command;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.lang3.SystemUtils;

public class CmdConst {

    public static final CommandLine LS = CommandLine.parse(SystemUtils.IS_OS_WINDOWS ? "cmd /c dir" : "ls");
    public static final CommandLine PWD = CommandLine.parse(SystemUtils.IS_OS_WINDOWS ? "cmd /c cd" : "pwd");
    public static final CommandLine CD_HOME = CommandLine.parse(SystemUtils.IS_OS_WINDOWS ? "cmd /c cd %HOMEPATH%" : "cd ~");

    public static final CommandLine JAVA_VERSION = CommandLine.parse("cmd /c java -version");
    public static final CommandLine JPS = CommandLine.parse("jps");
}
