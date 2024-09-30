package com.netease.lowcode.extension.filestorage.spi;

import com.netease.lowcode.extension.filestorage.UploadPatchFileStorageClient;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Map;

public class UploadPatchExtensionFileStorageClient implements UploadPatchFileStorageClient {
    private Object fsClient;
    private String type;
    private Method initMethod;
    private Method uploadMethod;
    private Method downloadMethod;
    private Method deleteMethod;

    private Method filterTargetFileMethod;

    public UploadPatchExtensionFileStorageClient(Object fsClient) {
        this.fsClient = fsClient;
        this.type = (String) ReflectionUtils.invokeMethod(ReflectionUtils.findMethod(fsClient.getClass(), "type"), fsClient);
        this.initMethod = ReflectionUtils.findMethod(fsClient.getClass(), "init", Map.class);
        this.uploadMethod = ReflectionUtils.findMethod(fsClient.getClass(), "upload", InputStream.class, String.class, Map.class);
        this.downloadMethod = ReflectionUtils.findMethod(fsClient.getClass(), "download", OutputStream.class, String.class, Map.class);
        this.deleteMethod = ReflectionUtils.findMethod(fsClient.getClass(), "delete", String.class, Map.class);
        this.filterTargetFileMethod = ReflectionUtils.findMethod(fsClient.getClass(), "filterTargetFile", Object.class);
    }

    @Override
    public String type() {
        return type;
    }

    @Override
    public void init(Map<String, String> config) {
        ReflectionUtils.invokeMethod(initMethod, fsClient, config);
    }

    @Override
    public String upload(InputStream inputStream, String targetPath, Map<String, String> payloads) throws IOException {
        return (String) ReflectionUtils.invokeMethod(uploadMethod, fsClient, inputStream, targetPath,payloads);
    }

    @Override
    public void download(OutputStream outputStream, String targetPath, Map<String, String> payloads) throws IOException {
        ReflectionUtils.invokeMethod(downloadMethod, fsClient, outputStream, targetPath,payloads);
    }

    @Override
    public void delete(String targetPath, Map<String, String> payloads) throws IOException {
        ReflectionUtils.invokeMethod(deleteMethod, fsClient, targetPath,payloads);
    }

    @Override
    public void filterTargetFile(Object fileObject) {
        ReflectionUtils.invokeMethod(filterTargetFileMethod, fsClient, fileObject);
    }
}
