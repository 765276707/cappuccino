package com.github.xzb617.cappuccino.server.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ValidationException;

public class FileValidator {

    public static void notNull(MultipartFile file, String errorMsg) {
        if (file == null) {
            throw new ValidationException(errorMsg);
        }
    }


    public static void supportExtensions(String fileName, String errorMsg, String ... exts) {
        String fileExt = fileName.substring(fileName.lastIndexOf("."));
        boolean flag = false;
        for (String ext : exts) {
            if (ext.equalsIgnoreCase(fileExt)) {
                flag = true;
                break;
            }
        }
        if (flag) {
            throw new ValidationException(errorMsg);
        }
    }

    public static void limitMaxsize(long fileSize, long maxFileSize, String errorMsg) {
        if (fileSize > maxFileSize) {
            throw new ValidationException(errorMsg);
        }
    }
}
