package com.netease.lowcode.extension;

import com.netease.lowcode.core.annotation.NaslLogic;
import com.netease.lowcode.extension.filestorage.UploadPatchFileStorageClient;
import com.netease.lowcode.extension.filestorage.UploadPatchFileStorageClientManager;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Aspect
@Component
public class UploadPatch {
    @Autowired
    private ApplicationContext context;
    @Value("${mybatis.type-handlers-package}")
    private String typeHandlerPackage;
    @Value("${file.upload.hashFileName:false}")
    private Boolean hashFileName;
    @Value("${spring.application.id}")
    private String appId;
    @Value("${custom.upload_encryptFileName:false}")
    private Boolean encryptFileName;
    @Value("${extensions.file_compress_tool.custom.compressArchiveType:7z}")
    private String compressArchiveType = "7z";
    @Value("${custom.api.context-path}")
    private String apiContextPath;

    @Value("${lcp.upload.sinkType}")
    private String sinkType;
    @Value("${lcp.upload.sinkPath}")
    private String sinkPath;
    @Value("${lcp.upload.ttl}")
    private Double uploadTtl;
    @Value("${file.upload.allowed-types:}")
    private String uploadAllowedTypes;

    @Resource
    private UploadPatchFileStorageClientManager spiManager;

    private final Logger log = LoggerFactory.getLogger(UploadPatch.class);

    @Pointcut("execution(* *..web.controller.FileUploadController.upload(..)) && args(body, file, service, request, response)")
    private void upload(String body, List<MultipartFile> file, String service, HttpServletRequest request, HttpServletResponse response){}

    @NaslLogic
    public static String test() {
        return "OK";
    }


    /**
     * 获取类的静态属性值
     *
     * @param className
     * @param arrName
     * @return
     * @throws ClassNotFoundException
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private Object getStaticArr(String className, String arrName) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Class<?> clazz = Class.forName(className);
        Field field = clazz.getDeclaredField(arrName);
        return field.get(null);
    }

    private Object getBeanArr(String className,String arrName) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Object bean = context.getBean(Class.forName(className));
        Field field = bean.getClass().getDeclaredField(arrName);
        field.setAccessible(true);
        return field.get(bean);
    }

    private Object getBeanArr(Object bean, String arrName) throws NoSuchFieldException, IllegalAccessException {
        Field field = bean.getClass().getDeclaredField(arrName);
        field.setAccessible(true);
        return field.get(bean);
    }

    @Around("upload(body, file, service, request, response)")
    public void aroundUpload(ProceedingJoinPoint joinPoint, String body, List<MultipartFile> file, String service, HttpServletRequest request, HttpServletResponse response) throws Throwable {

        //joinPoint.proceed();

        // 获取上传文件的存储类型
        String fsType = request.getParameter(UploadPatchConstants.UPLOAD_PARAMETER_FS_TYPE);
        fsType = ObjectUtils.defaultIfNull(fsType, sinkType);

        // 获取上传文件的访问权限
        String access = StringUtils.defaultString(request.getHeader(UploadPatchConstants.UPLOAD_HEADER_ACCESS), UploadPatchConstants.UPLOAD_ACCESS_PUBLIC);

        // 获取CDN加速
        Boolean viaOriginUrl = Boolean.parseBoolean(request.getParameter(UploadPatchConstants.UPLOAD_PARAMETER_FS_VIA_ORIGIN_URL));

        // 获取上传文件的过期时间
        Double ttl = Double.valueOf(StringUtils.defaultString(request.getHeader(UploadPatchConstants.UPLOAD_HEADER_TTL),
                String.valueOf(uploadTtl)));

        //附加数据获取的storagePath
        String storagePath = request.getParameter(UploadPatchConstants.UPLOAD_PARAMETER_STORAGE_PATH);

        // 指定要上传的文件路径
        String uploadPath = null;
        // 指定要上传的文件路径 如果storagePath不为空 且通过检验，则取值作为uploadPath最终的文件上传路径
        if (StringUtils.isNotBlank(storagePath)) {
            filterStoragePath(storagePath);
            uploadPath = storagePath;
        } else {
            uploadPath = UploadPatchFileUploadUtils.getUploadPath(request, UploadPatchFileUploadUtils.UPLOAD_API_PATH_PREFIX + "/");
        }

        //文件路径安全过滤
        uploadPathFilter(uploadPath);

        // 获取压缩参数
        Boolean isCompress = Boolean.parseBoolean(request.getParameter(UploadPatchConstants.UPLOAD_PARAMETER_FS_IS_COMPRESS));

        // 获取文件存储客户端
        UploadPatchFileStorageClient fileSystemSpi = spiManager.getFileSystemSpi(fsType);

        // 请求参数中获取 请求路径
        String path = StringUtils.defaultIfBlank(request.getParameter(UploadPatchConstants.UPLOAD_PARAMETER_PATH), "");

        if (null != fileSystemSpi) {
            List<String> filePaths = new ArrayList<>(file.size());
            for (MultipartFile multipartFile : file) {
                try (InputStream inputStream = multipartFile.getInputStream()) {

                    // 获取上传文件名
                    String originalFilename = multipartFile.getOriginalFilename();

                    // 对上传的文件名进行安全校验
                    fileNameFilter(originalFilename);

                    // 文件名安全处理 （防止XSS攻击）
                    String safeFilename = Encode.forHtml(originalFilename);

                    // 文件路径 安全处理 (防止XSS攻击)
                    uploadPath = Encode.forHtml(uploadPath);

                    //对象存储中的文件名需要和返回的不一致
                    Triple<String/*原始文件名称*/, String/*处理过后的文件名*/, String/*处理过后的文件名 + 后缀*/> fileNameTriple =
                            UploadPatchFileUploadUtils.handlerFileName(originalFilename, this.hashFileName, appId, this.encryptFileName);
                    safeFilename = fileNameTriple.getRight();

                    //要进行拼接的文件路径前缀 优先选取上传路径 ,如果没有则选取配置文件路径
                    String prefixFilePath = StringUtils.isNotBlank(uploadPath)? FilenameUtils.concat(sinkPath,uploadPath) : sinkPath;

                    //@since 3.9 默认需要加一级制品的目录
                    prefixFilePath = FilenameUtils.concat(prefixFilePath + File.separator, this.appId);

                    //生成最终存储的文件路径
                    String savePath = UploadPatchFileUploadUtils.generateSavePath(safeFilename, prefixFilePath, access, ttl,viaOriginUrl);

                    // 开启文件压缩功能
                    if (Boolean.TRUE.equals(isCompress) && StringUtils.isNotBlank(compressArchiveType)) {
                        String filePath = compress(inputStream, savePath, fileSystemSpi, request, fsType);
                        filePath = UploadPatchFileUploadUtils.replaceTargetFileSeg(filePath, fileNameTriple.getMiddle(), fileNameTriple.getLeft(), this.encryptFileName);
                        filePaths.add(filePath);
                    } else {
                        // 不开启文件压缩功能
                        String filePath = fileSystemSpi.upload(inputStream, savePath, formatRequestParameters(request));
                        filePath = UploadPatchFileUploadUtils.replaceTargetFileSeg(filePath, fileNameTriple.getMiddle(), fileNameTriple.getLeft(), this.encryptFileName);
                        filePaths.add(filePath);
                    }
                }
            }

            uploadLocalResponse(filePaths, request, response);
        } else {
            throw new UploadPatchHttpCodeException(UploadPatchErrorCodeEnum.FILESYSTEM_NOT_SUPPORT.code);
        }
    }

    private Map<String, String> formatRequestParameters(HttpServletRequest request) {
        if (CollectionUtils.isEmpty(request.getParameterMap())) {
            return new HashMap<>();
        }

        Map<String, String> result = new HashMap<>();
        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            if (null != entry.getValue()) {
                result.put(entry.getKey(), String.join(",", entry.getValue()));
            } else {
                result.put(entry.getKey(), null);
            }
        }

        return result;
    }

    public File inputStreamToFile(InputStream inputStream, String targetPath) throws IOException {
        File targetFile = new File(targetPath);
        targetFile.getParentFile().mkdirs();
        try (OutputStream outputStream = new FileOutputStream(targetFile)) {
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        }
        return targetFile;
    }
    private File createArchive(File file) throws Exception {

        File compressedFile = file;
        try {
            // 获取指定beanName对应的bean
            Object bean = context.getBean("fileCompressTool");
            if (bean != null) {
                // 获取方法名
                String methodName = "createArchive";
                // 获取方法参数类型
                Class<?>[] parameterTypes = new Class<?>[]{File.class};
                // 获取方法
                Method method = bean.getClass().getMethod(methodName, parameterTypes);
                if (method != null) {
                    // 调用方法
                    Object result = method.invoke(bean, file);
                    if (result instanceof File) {
                        compressedFile = (File) result;
                        return compressedFile;
                    }
                }
            }
        } catch (Exception e) {
            log.error("创建压缩文件时发生错误:", e);
        }
        return compressedFile;
    }
    private String compress(InputStream inputStream,String savePath,UploadPatchFileStorageClient fileSystemSpi,HttpServletRequest request,String fsType) throws Exception {
        // 生成临时文件
        File tempFile = inputStreamToFile(inputStream,savePath);
        // 获取到压缩文件
        File compressedFile = createArchive(tempFile);

        try (InputStream compressedInputStream = new FileInputStream(compressedFile)) {
            String uploadFilePath = savePath;
            if (!savePath.contains(compressedFile.getName())) {
                uploadFilePath += '.' + compressArchiveType;
            }
            String filePath = fileSystemSpi.upload(compressedInputStream, uploadFilePath, formatRequestParameters(request));
            return filePath;
        }
        finally {
            if (compressedFile != null && compressedFile.isFile() && !UploadPatchConstants.UPLOAD_TYPE_LOCAL.equalsIgnoreCase(fsType)) {
                compressedFile.delete();
            }
            // 关闭临时文件的流和删除临时文件
            if (tempFile != null && tempFile.isFile()) {
                Files.delete(tempFile.toPath());
            }
        }
    }

    private void fileNameFilter(String originalFilename) {
        // 对上传文件名的长度进行限制 Linux和Unix系统下的文件名最大长度是255个字符
        if (originalFilename != null && originalFilename.length() > 255) {
            throw new UploadPatchHttpCodeException(UploadPatchErrorCodeEnum.FILENAME_TOO_LONG.desc);
        }

        // 校验文件名是否符合要求
        if (!FilenameUtils.getName(originalFilename).matches("^[^;:><\\/\\\\*%]+(\\.[a-zA-Z0-9]+)?$")) {
            throw new UploadPatchHttpCodeException(UploadPatchErrorCodeEnum.FILENAME_NOT_ALLOWED.desc);
        }

        // 根据文件名后缀来判断文件类型是否允许上传
        String suffix = StringUtils.substringAfterLast(originalFilename, ".");
        if (StringUtils.isNotBlank(uploadAllowedTypes)) {
            List<String> allowedTypes = Arrays.asList(uploadAllowedTypes.split(";"));
            boolean isAllowed = allowedTypes.stream()
                    .map(String::trim)
                    .anyMatch(type -> suffix.equalsIgnoreCase(type));
            if (!isAllowed) {
                throw new UploadPatchHttpCodeException(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),UploadPatchErrorCodeEnum.FILE_TYPE_NOT_ALLOWED.desc);
            }
        }
    }

    private void uploadPathFilter(String uploadPath) {
        if(StringUtils.isNotBlank(uploadPath)) {
            Pattern pattern = Pattern.compile("^[a-zA-Z0-9_/-]*$");
            Matcher matcher = pattern.matcher(uploadPath);
            if (!matcher.matches()) {
                // 路径不合法，抛出异常或返回错误提示
                throw new UploadPatchHttpCodeException(HttpStatus.NOT_FOUND.value(),UploadPatchErrorCodeEnum.FILEPATH_NOT_ALLOWED.desc);
            }

            // 对上传路径进行合法性校验
            String[] pathSegs = uploadPath.split("/");
            for (String seg : pathSegs) {
                if (seg.equals("..")) {
                    throw new UploadPatchHttpCodeException(UploadPatchErrorCodeEnum.FILEPATH_NOT_ALLOWED.desc);
                }
                if (seg.equals(".")) {
                    throw new UploadPatchHttpCodeException(UploadPatchErrorCodeEnum.FILEPATH_NOT_ALLOWED.desc);
                }
            }

            // 限制上传路径的长度
            if (pathSegs.length > 255) {
                throw new UploadPatchHttpCodeException(UploadPatchErrorCodeEnum.FILEPATH_TOO_LONG.desc);
            }
        }
    }

    private void filterStoragePath(String storagePath) {
        // 对上传文件路径的长度进行限制 Linux和Unix系统下的文件名最大长度是255个字符
        if (StringUtils.isBlank(storagePath)){
            return;
        }

        if (storagePath.length() > 255) {
            throw new UploadPatchHttpCodeException(UploadPatchErrorCodeEnum.FILENAME_TOO_LONG.desc);
        }

        //校验文件路径是否符合要求 不允许出现 / \ : * ? " < > | 中文 等非 ASCII 字符
        if (storagePath.matches("[/\\\\:*?\"<>|']") || storagePath.matches("[^\\x00-\\x7F]+")) {
            throw new UploadPatchHttpCodeException(UploadPatchErrorCodeEnum.FILENAME_NOT_ALLOWED.desc);
        }
    }

    private void uploadLocalResponse(List<String> paths, HttpServletRequest request, HttpServletResponse response) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        String prefix = StringUtils.equals("/",apiContextPath)?"":apiContextPath;

        String fsType = request.getParameter("lcapFsType");
        List<String> formattedPathResults = new ArrayList<>(paths.size());
        //相对路径
        List<String> relativePathResults = new ArrayList<>(paths.size());
        Map<String,String> pathToRelativePathMap = new HashMap<>();
        for (String path : paths) {
            if (!UploadPatchDomainUtil.matchSupportHttpProtocolPrefix(path)) {
                path = path.startsWith(File.separator) ? path.substring(1) : path;
                String fileDownloadPath;
                String relativePath;
                if (StringUtils.isEmpty(request.getContextPath())) {
                    fileDownloadPath = fileDownloadPathFromRequest(request, path,prefix);
                    relativePath = FilenameUtils.concat(prefix+"/upload", path);
                } else if(isDownloadPathFromContextPath(request)){
                    // contextPath 写全路径 携带Http https 协议的情况，这种情况不需要再拼接协议等信息
                    fileDownloadPath = String.join("/", request.getContextPath(),
                            "/upload", path);
                    relativePath = fileDownloadPath;
                } else {
                    //剩余情况 用当前域名进行拼接
                    String protocol = request.getScheme(); // 获取协议
                    String serverName = request.getServerName(); // 获取域名或IP
                    int serverPort = request.getServerPort(); // 获取端口号
                    String contextPath = request.getContextPath(); // 获取上下文路径

                    // 拼接文件下载路径
                    fileDownloadPath = String.format("%s://%s:%d%s%s/%s", protocol, serverName, serverPort, contextPath, "/upload", path);
                    relativePath = contextPath + FilenameUtils.concat("/upload", path);
                }


                fileDownloadPath = appendFsTypeResponse(fileDownloadPath, fsType);

                fileDownloadPath = UploadPatchFileUploadUtils.convertToUnifiedPath(fileDownloadPath);
                relativePath = UploadPatchFileUploadUtils.convertToUnifiedPath(relativePath);

                formattedPathResults.add(fileDownloadPath);
                relativePathResults.add(relativePath);
                pathToRelativePathMap.put(fileDownloadPath,relativePath);
            } else {
                path = UploadPatchFileUploadUtils.convertToUnifiedPath(path);
                formattedPathResults.add(path);
            }
        }

        Object result = null;
        //String userId = null == UserContext.getCurrentUser() ? "" : UserContext.getCurrentUser().getUserId();
        if (formattedPathResults.size() == 1) {
            // 单文件返回string，这里兼容老实现
            result = new UploadResult(formattedPathResults.get(0),pathToRelativePathMap.get(formattedPathResults.get(0)));
            //log.info("制品: {}, 用户: {}, 上传文件: {}", this.appId, userId, ((UploadResult)result).getResult());
        } else {
            result = new BatchUploadResult(formattedPathResults,relativePathResults);
            //log.info("制品: {}, 用户: {}, 上传文件: {}", this.appId, userId, JacksonUtils.toJson(((BatchUploadResult)result).getResult()));
        }



        response.setContentType("application/json;charset=UTF-8");
        Object objectMapperBean = context.getBean(Class.forName("com.fasterxml.jackson.databind.ObjectMapper"));
        Method writeValueAsBytes = objectMapperBean.getClass().getDeclaredMethod("writeValueAsBytes", Object.class);
        IOUtils.write((byte[]) writeValueAsBytes.invoke(objectMapperBean,result), response.getOutputStream());
    }

    private String fileDownloadPathFromRequest(HttpServletRequest request, String path,String prefix) {
        return ( request.getScheme() + "://" + request.getServerName() +
                ( 80 == request.getServerPort() ? "" : ":" + request.getServerPort() ) +
                FilenameUtils.concat(prefix+"/upload", path) );
    }
    private boolean isDownloadPathFromContextPath(HttpServletRequest request) {
        return !StringUtils.isEmpty(request.getContextPath()) &&
                ( request.getContextPath().startsWith("http") || request.getContextPath().startsWith("https") );
    }
    private String appendFsTypeResponse(String fileDownloadPath, String fsType) {
        if (!StringUtils.isEmpty(fsType)) {
            return fileDownloadPath.indexOf('?') >= 0 ?
                    fileDownloadPath + "&" + "lcapFsType" + "=" + fsType :
                    fileDownloadPath + "?" + "lcapFsType" + "=" + fsType;
        }

        return fileDownloadPath;
    }
    class UploadResult {
        private int code = 200;
        private boolean success = true;
        private String msg = "success";
        private String result;

        //相对路径
        private String filePath;

        public UploadResult() {}
        public UploadResult(String result) {
            this.result = result;
        }

        public UploadResult(String result,String filePath) {
            this.result = result;
            this.filePath = filePath;
        }

        public String getFilePath() {return filePath;}

        public void setFilePath(String filePath) {this.filePath = filePath;}

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }

    class BatchUploadResult {
        private int code = 200;
        private boolean success = true;
        private String msg = "success";
        private List<String> result;

        public List<String> getFilePath() {
            return filePath;
        }

        public void setFilePath(List<String> filePath) {
            this.filePath = filePath;
        }

        private List<String> filePath;

        public BatchUploadResult() {}
        public BatchUploadResult(List<String> result) {
            this.result = result;
        }

        public BatchUploadResult(List<String> result,List<String> filePath) {
            this.result = result;
            this.filePath = filePath;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public List<String> getResult() {
            return result;
        }

        public void setResult(List<String> result) {
            this.result = result;
        }
    }
}