package com.netease.lowcode.extension.command;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;

import java.io.IOException;
import java.util.List;

public class Command {

    public static void exec(List<String> cmdList) {
        DefaultExecutor executor = new DefaultExecutor();
        ExecuteWatchdog watchdog = new ExecuteWatchdog(60000);
        executor.setWatchdog(watchdog);
        try {
            executor.setStreamHandler(new CmdExecStreamHandler());
            executor.execute(parseCmd(cmdList));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static CommandLine parseCmd(List<String> cmdList) {
        String cmd = CmdConst.PREFIX + String.join(" ; ", cmdList);
        System.out.println("执行命令: " + cmd);
        return CommandLine.parse(cmd);
    }

}
