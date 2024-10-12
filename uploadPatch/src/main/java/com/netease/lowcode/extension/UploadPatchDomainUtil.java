package com.netease.lowcode.extension;

import org.apache.commons.lang3.StringUtils;

public class UploadPatchDomainUtil {
    public static final String SCHEME_HTTP_PREFIX = "http://";
    public static final String SCHEME_HTTPS_PREFIX = "https://";
    public static boolean matchSupportHttpProtocolPrefix(String serviceDomain){
        if(StringUtils.isBlank(serviceDomain)) {
            return false;
        }
        return serviceDomain.startsWith(SCHEME_HTTP_PREFIX) || serviceDomain.startsWith(SCHEME_HTTPS_PREFIX);
    }
}
