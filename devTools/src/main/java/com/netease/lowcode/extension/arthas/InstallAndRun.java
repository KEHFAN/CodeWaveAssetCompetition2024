package com.netease.lowcode.extension.arthas;

import com.netease.lowcode.core.annotation.NaslLogic;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class InstallAndRun {

    @NaslLogic
    public static String copy2() {
        try {
            InputStream inputStream = InstallAndRun.class.getClassLoader().getResourceAsStream("arthas-bin.zip");
            File targetFile = new File("/", "arthas-bin.zip");

            if (targetFile.exists()) {
                targetFile.delete();
            }

            FileUtils.copyToFile(inputStream, targetFile);
            inputStream.close();
            return "";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
