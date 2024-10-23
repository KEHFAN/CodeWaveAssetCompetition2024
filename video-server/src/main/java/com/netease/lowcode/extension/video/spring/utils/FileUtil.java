package com.netease.lowcode.extension.video.spring.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class FileUtil {

    public static InputStream getFileInputStream(String urlStr) throws IOException {
        // url中包含中文，可能会400错误，需要对url进行编码处理
        // 目前 低版本ide(3.8) 生成的url 包含中文
        //     高版本ide 文件名放到的query参数中，不会有影响

        // 仅处理中文
        URL url = new URL(urlStr);
        String path = url.getPath();
        String[] split = path.split("/");
        StringBuilder sb = new StringBuilder();
        for (String s : split) {
            sb.append("/").append(URLEncoder.encode(s, StandardCharsets.UTF_8.name()));
        }
        if (StringUtils.isNotBlank(url.getQuery())) {

            sb.append("?");
            String[] params = url.getQuery().split("&");
            for (String param : params) {
                String[] entry = param.split("=");
                String key = entry[0];
                String val = entry[1];

                if (!sb.toString().endsWith("?")) {
                    sb.append("&");
                }

                sb.append(URLEncoder.encode(key, StandardCharsets.UTF_8.name()));
                sb.append("=");
                sb.append(URLEncoder.encode(val, StandardCharsets.UTF_8.name()));
            }

        }
        URL newUrl = new URL(url.getProtocol(), url.getHost(), url.getPort(), sb.toString().replace("//","/"));
        HttpURLConnection connection = (HttpURLConnection) newUrl.openConnection();
        connection.setConnectTimeout(3 * 1000);
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.82 Safari/537.36");
        return newUrl.openStream();
    }

    public static void saveFile(String url, String filename) throws IOException {
        InputStream inputStream = getFileInputStream(url);

        FileOutputStream fos = new FileOutputStream(filename);

        byte[] buffer = new byte[1024];
        int read;
        while ((read = inputStream.read(buffer)) != -1) {
            fos.write(buffer, 0, read);
        }

        inputStream.close();
        fos.flush();
        fos.close();
    }
}
