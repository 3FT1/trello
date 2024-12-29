package com.example.trello.util;

import org.apache.commons.io.FilenameUtils;

public class FileUploadUtil {
    private static final String[] ALLOWED_EXTENSIONS = {"jpg", "png", "gif", "jpeg"};

    public static boolean isAllowedExtension(String fileName) {
        String ext = FilenameUtils.getExtension(fileName).toLowerCase();
        for (String allowedExt : ALLOWED_EXTENSIONS) {
            if (allowedExt.equals(ext)) {
                return true;
            }
        }
        return false;
    }
}
