package com.netease.lowcode.extension.video.spring.utils;

import java.util.Random;

public class StringGenerator {
    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String generator(int len) {
        StringBuilder sb = new StringBuilder(len);
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            sb.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return sb.toString();
    }
}
