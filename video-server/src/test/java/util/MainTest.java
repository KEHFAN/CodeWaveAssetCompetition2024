package util;

import com.netease.lowcode.extension.video.spring.utils.FileUtil;
import com.netease.lowcode.extension.video.spring.utils.StringGenerator;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class MainTest {
    public static void main1(String[] args) throws UnsupportedEncodingException, MalformedURLException, URISyntaxException {
        System.out.println(UUID.randomUUID());
        System.out.println(StringGenerator.generator(10));
        System.out.println(StandardCharsets.UTF_8.displayName());

        String url = "https://dev-tianyuan38test-defaulttenant.lcap.codewave-test.163yun.com:443/upload/app/fb5e7ed0-4422-45f8-b862-5fbd6e1dfd99/tDE90eE8_9194231697_uhd-副本_20241023101744091.mp4";
        System.out.println(URLEncoder.encode(url));
        System.out.println(URLEncoder.encode(url,StandardCharsets.UTF_8.name()));

        URL uuu = new URL(url);
        System.out.println(uuu.toURI());
    }

    public static void main(String[] args) throws IOException {
        //String url = "https://dev-tianyuan38test-defaulttenant.lcap.codewave-test.163yun.com:443/upload/app/fb5e7ed0-4422-45f8-b862-5fbd6e1dfd99/tDE90eE8_9194231697_uhd-副本_20241023101744091.mp4";
        //String url2 = "https://dev-tianyuan38test-defaulttenant.lcap.codewave-test.163yun.com:443/upload/app/fb5e7ed0-4422-45f8-b862-5fbd6e1dfd99/tDE90eE8_9194231697_uhd-%E5%89%AF%E6%9C%AC_20241023101744091.mp4";
        String url = "https://dev-videotest1-kehfan.app.codewave.163.com:443/upload/?fileName=tDE90eE8_9194231697_uhd-副本.mp4&fut=1729664619161&ai=779efa5d-42f3-4fbc-9ac5-7bfdaece528e&con=lcap_default_connection";
        FileUtil.getFileInputStream(url);
    }
}
