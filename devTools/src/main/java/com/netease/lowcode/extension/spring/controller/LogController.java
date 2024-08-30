package com.netease.lowcode.extension.spring.controller;

import com.netease.lowcode.extension.arthas.InstallAndRun;
import com.netease.lowcode.extension.command.CmdChains;
import com.netease.lowcode.extension.command.Command;
import com.netease.lowcode.extension.logs.parse.NativeLogParse;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@RestController
public class LogController {

    @GetMapping("/rest/test")
    public String test() {
        InputStream inputStream = InstallAndRun.class.getClassLoader().getResourceAsStream("log.html");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        return reader.lines().collect(Collectors.joining(System.lineSeparator()));
    }

    /**
     * 查看native日志
     * /rest/query/native?num=5
     *
     * @param num
     * @param response
     */
    @GetMapping("/rest/query/native")
    public void queryNativeLog(Integer num, HttpServletResponse response) {

        response.setContentType(MediaType.TEXT_PLAIN_VALUE);
        response.setCharacterEncoding("utf-8");

        RestExecStreamHandler handler = new RestExecStreamHandler() {
            @Override
            public void setProcessErrorStream(InputStream inputStream) throws IOException {
                IOUtils.copy(inputStream, response.getOutputStream());
            }

            @Override
            public void setProcessOutputStream(InputStream inputStream) throws IOException {
                IOUtils.copy(inputStream, response.getOutputStream());
            }
        };

        if (num > 0) {
            Command.exec(CmdChains.CAT_NATIVE_LOG_LAST(num), handler);
        } else {
            Command.exec(CmdChains.CAT_NATIVE_LOG_FIRST(Math.abs(num)), handler);
        }
    }

    /**
     * 匹配native日志，过滤掉无用信息
     *
     * @param num
     * @param response
     */
    @GetMapping("/rest/match/native")
    public void matchNativeLog(Integer num, HttpServletResponse response) {
        response.setContentType(MediaType.TEXT_PLAIN_VALUE);
        response.setCharacterEncoding("utf-8");

        RestExecStreamHandler handler = new RestExecStreamHandler() {
            @Override
            public void setProcessErrorStream(InputStream inputStream) throws IOException {
                IOUtils.copy(inputStream, response.getOutputStream());
            }

            @Override
            public void setProcessOutputStream(InputStream inputStream) throws IOException {
                InputStreamReader isReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader reader = new BufferedReader(isReader);
                String content = reader.lines().collect(Collectors.joining(System.lineSeparator()));
                NativeLogParse.parseString(content, s -> {
                    try (PrintWriter out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8), true)) {
                        out.write(s);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        };

        if (num > 0) {
            Command.exec(CmdChains.CAT_NATIVE_LOG_LAST(num), handler);
        } else {
            Command.exec(CmdChains.CAT_NATIVE_LOG_FIRST(Math.abs(num)), handler);
        }
    }

}
