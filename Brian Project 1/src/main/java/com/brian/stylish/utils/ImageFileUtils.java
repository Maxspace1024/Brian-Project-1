package com.brian.stylish.utils;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class ImageFileUtils {

    @Value("${directory.image.name}")
    private static final String DIRECTORY_NAME = "stylish_images";

    public static String saveFile(MultipartFile file, String filename) throws IOException {
        // 確保目錄存在
        File dir = new File(DIRECTORY_NAME);
        if (!dir.exists()) {
            dir.mkdir();
        }

        Path filePath = Paths.get(DIRECTORY_NAME, filename);

        Files.write(filePath, file.getBytes());

        return filename;
    }

    public static void deleteFile(String filename) throws IOException {
        Path filePath = Paths.get(DIRECTORY_NAME, filename);
        Files.delete(filePath);
    }

    public static byte[] getFile(String filename) throws IOException {
        Path filePath = Paths.get(DIRECTORY_NAME, filename);
        if (!Files.exists(filePath)) {
            return new byte[0];
        }

        return Files.readAllBytes(filePath);
    }

    public static String createUuidFilename(MultipartFile file) {
        String uuid = UUID.randomUUID().toString();
        String extname = FilenameUtils.getExtension(file.getOriginalFilename());
        return String.format("%s.%s", uuid, extname);
    }
}
