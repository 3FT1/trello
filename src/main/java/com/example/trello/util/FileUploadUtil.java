package com.example.trello.util;

import org.apache.commons.io.FilenameUtils;

public class FileUploadUtil {
    private static final String[] ALLOWED_BOARD_IMAGE = {"jpg", "png", "gif", "jpeg"};
    private static final String[] ALLOWED_ATTACHMENT_EXTENSIONS = {"jpg", "png", "jpeg", "gif", "pdf", "csv"};

    public static boolean isAllowedExtension(String fileName) {
        String ext = FilenameUtils.getExtension(fileName).toLowerCase();
        for (String allowedExt : ALLOWED_BOARD_IMAGE) {
            if (allowedExt.equals(ext)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAllowedAttachmentExtension(String fileName) {
        String ext = FilenameUtils.getExtension(fileName).toLowerCase();
        for (String allowedExt : ALLOWED_ATTACHMENT_EXTENSIONS) {
            if (allowedExt.equals(ext)) {
                return true;
            }
        }
        return false;
    }
}
