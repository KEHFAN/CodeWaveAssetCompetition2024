package com.netease.lowcode.extension.command;

import com.netease.lowcode.extension.utils.StreamUtil;
import org.apache.commons.exec.ExecuteStreamHandler;

import java.io.*;

public class CmdExecStreamHandler implements ExecuteStreamHandler {

    @Override
    public void setProcessErrorStream(InputStream inputStream) throws IOException {
        StreamUtil.readInputStream(inputStream);
    }

    @Override
    public void setProcessInputStream(OutputStream outputStream) throws IOException {

    }

    @Override
    public void setProcessOutputStream(InputStream inputStream) throws IOException {
        StreamUtil.readInputStream(inputStream);
    }

    @Override
    public void start() throws IOException {

    }

    @Override
    public void stop() throws IOException {

    }
}
