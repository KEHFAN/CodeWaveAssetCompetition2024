package com.netease.lowcode.extension.utils;

import java.io.*;

public class StreamUtil {

    public static void readInputStream(InputStream inputStream) throws IOException {
        // 控制台使用 GBK
        InputStreamReader isReader = new InputStreamReader(inputStream, "utf-8");
        BufferedReader reader = new BufferedReader(isReader);
        String line;
        while ((line= reader.readLine()) != null){
            System.out.println(line);
        }
        reader.close();
        isReader.close();
        inputStream.close();
    }

}
