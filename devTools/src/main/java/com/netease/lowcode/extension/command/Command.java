package com.netease.lowcode.extension.command;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;

import java.io.IOException;

public class Command {

    public static void exec(CommandLine cmd) {
        DefaultExecutor executor = new DefaultExecutor();
        ExecuteWatchdog watchdog = new ExecuteWatchdog(60000);
        executor.setWatchdog(watchdog);
        try {
            executor.setStreamHandler(new CmdExecStreamHandler());
            executor.execute(cmd);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
