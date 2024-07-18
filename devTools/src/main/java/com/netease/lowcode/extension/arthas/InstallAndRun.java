package com.netease.lowcode.extension.arthas;

import com.netease.lowcode.core.annotation.NaslLogic;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

public class InstallAndRun {

    @NaslLogic
    public static String start() {
        try {
            File file = new File(ResourceUtils.getURL("classpath:arthas-bin.zip").getFile());
            return file.getAbsolutePath();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
