package com.netease.lowcode.extension.video.spring.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileUtil {

    public static InputStream getFileInputStream(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(3 * 1000);
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.82 Safari/537.36");
        return url.openStream();
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
