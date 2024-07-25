package com.netease.lowcode.extension.spring.controller;

import com.alibaba.fastjson2.JSONObject;
import com.netease.lowcode.extension.command.Command;
import com.netease.lowcode.extension.utils.HttpUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@RestController
public class ArthasController {

    /**
     * 启动arthas服务
     * 启动后使用httpapi交互 https://arthas.aliyun.com/doc/http-api.html
     *
     * [1] 执行 /rest/start
     * [2] 执行 /rest/start?pid=xxx
     * [3] 执行 /rest/start
     *
     * @param response
     */
    @GetMapping("/rest/start")
    public void start(Integer pid, HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.TEXT_PLAIN_VALUE);
        response.setCharacterEncoding("utf-8");

        InputStream inputStream = ArthasController.class.getClassLoader().getResourceAsStream("arthas-bin.zip");
        File targetFile = new File("/", "arthas-bin.zip");
        if (!targetFile.exists()) {
            FileUtils.copyToFile(inputStream, targetFile);

            // 解压
            byte[] buffer = new byte[1024];
            try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(targetFile))) {
                ZipEntry zipEntry = zipInputStream.getNextEntry();
                while (zipEntry != null) {
                    String fileName = zipEntry.getName();
                    File newFile = new File("/arthas-bin", fileName);

                    if (zipEntry.isDirectory()) {
                        newFile.mkdirs();
                    } else {
                        new File(newFile.getParent()).mkdirs();
                        try (FileOutputStream fos = new FileOutputStream(newFile)) {
                            int len;
                            while ((len = zipInputStream.read(buffer)) > 0) {
                                fos.write(buffer, 0, len);
                            }
                        }
                    }
                    zipEntry = zipInputStream.getNextEntry();
                }
                zipInputStream.closeEntry();
            }
        }

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
        if (Objects.nonNull(pid)) {
            Command.exec(Collections.singletonList("java -jar /arthas-bin/arthas-boot.jar " + pid + " &"), handler);
        } else {
            Command.exec(Collections.singletonList("jps -l"), handler);
        }
    }

    /**
     * stop - 关闭 Arthas 服务端，所有 Arthas 客户端全部退出
     *
     * @param response
     */
    @GetMapping("/rest/stop")
    public void stop(HttpServletResponse response) {
        File targetFile = new File("/", "arthas-bin.zip");
        if (targetFile.exists()) {
            targetFile.delete();
        }

        // 删除 解压的 目录。
    }

    @GetMapping("/rest/version")
    public void version(HttpServletResponse response) throws IOException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action", "exec");
        jsonObject.put("command", "version");

        post(response, jsonObject);
    }

    // 默认10分钟自动终端、自动关闭会话，如需手动，需要显示设置参数autoClose=false
    @GetMapping("/rest/session/init")
    public void initSession(@RequestParam(defaultValue = "true") Boolean autoClose, HttpServletResponse response) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action", "init_session");

        post(response, jsonObject);
    }

    @GetMapping("/rest/session/close")
    public void closeSession(String sessionId, HttpServletResponse response) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action", "close_session");
        jsonObject.put("sessionId", sessionId);

        post(response, jsonObject);
    }

    @GetMapping("/rest/session/query")
    public void queryResult(String sessionId, String consumerId, HttpServletResponse response) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action", "pull_results");
        jsonObject.put("sessionId", sessionId);
        jsonObject.put("consumerId", consumerId);

        post(response, jsonObject);
    }

    @GetMapping("/rest/session/interrupt")
    public void interruptExec(String sessionId, HttpServletResponse response) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action", "interrupt_job");
        jsonObject.put("sessionId", sessionId);

        post(response, jsonObject);
    }

    // trace - 方法内部调用路径，并输出方法路径上的每个节点上耗时
    @GetMapping("/rest/session/trace")
    public void trace(String sessionId,HttpServletResponse response) throws IOException {
        asyncExec(sessionId,"",response);
    }

    // sc -E .*Logic1.* 查询类信息
    @GetMapping("/rest/session/sc")
    public void sc(String sessionId, String logicName, HttpServletResponse response) throws IOException {
        asyncExec(sessionId, "sc -E .*" + logicName + ".*", response);
    }

    private void asyncExec(String sessionId, String command, HttpServletResponse response) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action", "async_exec");
        jsonObject.put("sessionId", sessionId);
        jsonObject.put("command", command);

        post(response, jsonObject);
    }

    private static void post(HttpServletResponse response, JSONObject jsonObject) throws IOException {
        HttpUtil.doPost("http://localhost:8563/api", jsonObject.toString(), body -> {
            try (PrintWriter out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8), true)) {
                out.write(body);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
