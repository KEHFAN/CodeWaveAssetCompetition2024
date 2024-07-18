package com.netease.lowcode.extension.utils;

import java.io.*;

public class StreamUtil {

    public static void readInputStream(InputStream inputStream) throws IOException {
        InputStreamReader isReader = new InputStreamReader(inputStream, "GBK");
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
