package com.netease.lowcode.extension.filestorage;

import com.netease.lowcode.extension.UploadPatchConstants;
import com.netease.lowcode.extension.filestorage.spi.UploadPatchExtensionFileStorageClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

import static com.netease.lowcode.extension.filestorage.UploadPatchFileStorageClient.UPLOAD_CONFIG_KEY_S3_ACCESS;

@Component
public class UploadPatchFileStorageClientManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadPatchFileStorageClientManager.class);
    private static final String FILESYSTEM_SPI_CLASS = "com.netease.lowcode.extension.LcapFileSystemSpi";

    @Value("${lcp.upload.sinkType}")
    private String sinkType;
    @Value("${lcp.upload.sinkPath}")
    private String sinkPath;
    @Value("${lcp.upload.s3Address}")
    private String s3Address;
    @Value("${lcp.upload.s3AccessKey}")
    private String s3AccessKey;
    @Value("${lcp.upload.s3SecretKey}")
    private String s3SecretKey;
    @Value("${lcp.upload.s3Bucket}")
    private String s3Bucket;
    @Value("${lcp.upload.cdnAddress}")
    private String cdnAddress;
    @Value("${lcp.upload.access}")
    private String access;

//    @Resource
//    private ExtensionProperties extensionProperties;

    @Resource
    private List<UploadPatchFileStorageClient> fileSystemClients;

    Map<String, UploadPatchFileStorageClient> fileSystemSpiCache = new HashMap<>();

    @PostConstruct
    public void init() {
        loadFileSystemSpi();
    }

    public synchronized void loadFileSystemSpi() {
        // 加载默认支持的文件系统spi
        loadBuiltInFileSystemSpi();

        // 加载拓展的文件系统客户端实现
        loadExtendsionFileSystemSpi();
    }

    /**
     * 系统默认支持的文件系统spi
     */
    private void loadBuiltInFileSystemSpi() {
        Map<String, String> builtInConfigs = new HashMap<>();
        builtInConfigs.put(UploadPatchConstants.UPLOAD_CONFIG_KEY_FILE_STORAGE_TYPE, sinkType);
        builtInConfigs.put(UploadPatchConstants.UPLOAD_CONFIG_LOCAL_BASE_PATH, sinkPath);
        builtInConfigs.put(UploadPatchConstants.UPLOAD_CONFIG_KEY_S3_ADDRESS, s3Address);
        builtInConfigs.put(UploadPatchConstants.UPLOAD_CONFIG_KEY_S3_ACCESS_KEY, s3AccessKey);
        builtInConfigs.put(UploadPatchConstants.UPLOAD_CONFIG_KEY_S3_SECRET_KEY, s3SecretKey);
        builtInConfigs.put(UploadPatchConstants.UPLOAD_CONFIG_KEY_S3_BUCKET, s3Bucket);
        builtInConfigs.put(UploadPatchConstants.UPLOAD_CONFIG_KEY_CDN_ADDRESS,cdnAddress);
        builtInConfigs.put(UPLOAD_CONFIG_KEY_S3_ACCESS, access);

        for (UploadPatchFileStorageClient fileSystemSpi : fileSystemClients) {
            fileSystemSpi.init(builtInConfigs);
            for (String type : fileSystemSpi.types()) {
                fileSystemSpiCache.put(type, fileSystemSpi);
            }
        }
    }

    /**
     * 拓展的文件系统spi
     */
    private void loadExtendsionFileSystemSpi() {
        try {
            Class fsClass = Class.forName(FILESYSTEM_SPI_CLASS);
            ServiceLoader.load(fsClass).forEach(service->{
                UploadPatchExtensionFileStorageClient extensionFileSystem = new UploadPatchExtensionFileStorageClient(service);
//                extensionFileSystem.init(extensionProperties);
                fileSystemSpiCache.put(extensionFileSystem.type(), extensionFileSystem);
                LOGGER.info("自定义文件系统spi " + extensionFileSystem.type() + "加载成功");
            });
        } catch (ClassNotFoundException e) {
            LOGGER.info("没有自定义拓展的文件系统spi实现");
        }
    }

    public UploadPatchFileStorageClient getFileSystemSpi(String type) {
        return fileSystemSpiCache.get(type);
    }
}
