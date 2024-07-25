package com.netease.lowcode.extension.utils;

import okhttp3.*;

import java.io.IOException;
import java.util.function.Consumer;

public class HttpUtil {

    public static void doPost(String url, String json, Consumer<String> result) throws IOException {
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            result.accept(response.body().string());
        }
    }

}
