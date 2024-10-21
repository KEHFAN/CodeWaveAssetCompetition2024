package com.netease.lowcode.extension.video.structure;

import com.netease.lowcode.core.annotation.NaslStructure;

@NaslStructure
public class Response {
    public String url;

    public static Response OK(String url) {
        Response response = new Response();
        response.setUrl(url);
        return response;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
