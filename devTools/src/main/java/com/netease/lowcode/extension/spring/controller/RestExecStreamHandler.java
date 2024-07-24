package com.netease.lowcode.extension.spring.controller;

import org.apache.commons.exec.ExecuteStreamHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class RestExecStreamHandler implements ExecuteStreamHandler {

    @Override
    public void setProcessErrorStream(InputStream inputStream) throws IOException {

    }

    @Override
    public void setProcessInputStream(OutputStream outputStream) throws IOException {

    }

    @Override
    public void setProcessOutputStream(InputStream inputStream) throws IOException {

    }

    @Override
    public void start() throws IOException {

    }

    @Override
    public void stop() throws IOException {

    }
}
