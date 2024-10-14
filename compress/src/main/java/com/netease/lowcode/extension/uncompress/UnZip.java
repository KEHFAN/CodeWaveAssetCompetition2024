package com.netease.lowcode.extension.uncompress;

import com.netease.lowcode.extension.compress.FileUtil;
import net.lingala.zip4j.io.inputstream.ZipInputStream;
import net.lingala.zip4j.model.LocalFileHeader;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class UnZip {

    public static Result unzipWithPassword(String zipUrl, String extractFile, String password) {

        if (StringUtils.isBlank(password)) {
            return Result.FAIL("密码为空");
        }

        try {
            Result ok = Result.OK();
            ZipInputStream zis = new ZipInputStream(FileUtil.getFileInputStream(zipUrl));
            zis.setPassword(password.toCharArray());
            LocalFileHeader entry;
            while ((entry = zis.getNextEntry()) != null) {

                String fileName = entry.getFileName();

                if (StringUtils.isBlank(extractFile)) {
                    ok.setMessage("未指定提取文件，仅返回文件列表");
                    ok.add(fileName, null);
                }

                if (StringUtils.equals(fileName, extractFile)) {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int read = 0;
                    while ((read = zis.read(buffer, 0, buffer.length)) != -1) {
                        bos.write(buffer, 0, read);
                    }
                    // 提取该文件转string
                    ok.add(extractFile, bos.toString(StandardCharsets.UTF_8.name()));
                    break;
                }
            }

            return ok;
        } catch (IOException e) {
            return Result.FAIL(e.getMessage(), Arrays.toString(e.getStackTrace()));
        }
    }


    /**
     *
     * @param zipUrl            传入的zip文件url
     * @param extractFile       需要提取的文件的相对路径
     * @return
     */

    public static Result unzip(String zipUrl, String extractFile) {
        try {

            ZipArchiveInputStream zis = new ArchiveStreamFactory().createArchiveInputStream(
                    ArchiveStreamFactory.ZIP, FileUtil.getFileInputStream(zipUrl)
            );

            ZipArchiveEntry zipEntry;

            Result ok = Result.OK();

            while ((zipEntry = zis.getNextEntry()) != null) {

                String fileName = zipEntry.getName();

                if (StringUtils.isBlank(extractFile)) {
                    ok.setMessage("未指定提取文件，仅返回文件列表");
                    ok.add(fileName, null);
                }

            }

            zis.close();

            return ok;
        } catch (IOException e) {
            return Result.FAIL(e.getMessage(), Arrays.toString(e.getStackTrace()));
        } catch (ArchiveException e) {
            throw new RuntimeException(e);
        }
    }
}
