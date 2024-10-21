package util;

import com.netease.lowcode.extension.video.spring.utils.StringGenerator;

import java.util.UUID;

public class MainTest {
    public static void main(String[] args) {
        System.out.println(UUID.randomUUID());
        System.out.println(StringGenerator.generator(10));
    }
}
