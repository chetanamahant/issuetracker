package com.example.issuetracker.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public class FileUploadUtil {

    // ✅ Allowed file types (screenshots)
    private static final List<String> ALLOWED_TYPES = List.of(
            "image/png",
            "image/jpeg",
            "image/jpg"
    );

    // ✅ Max file size: 5 MB
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    // 🔍 Validate file
    public static void validateFile(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is empty");
        }

        if (!ALLOWED_TYPES.contains(file.getContentType())) {
            throw new IllegalArgumentException("Invalid file type. Only PNG/JPEG allowed");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("File size exceeds 5MB limit");
        }
    }

    // 📦 Extract file bytes
    public static byte[] getFileBytes(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file content");
        }
    }

    // 📄 Get safe file name
    public static String getFileName(MultipartFile file) {
        return file.getOriginalFilename();
    }

    // 📏 File size
    public static long getFileSize(MultipartFile file) {
        return file.getSize();
    }

    // 📎 File content type
    public static String getFileType(MultipartFile file) {
        return file.getContentType();
    }
}
