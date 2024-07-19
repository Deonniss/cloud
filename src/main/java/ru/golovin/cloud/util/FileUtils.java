package ru.golovin.cloud.util;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public final class FileUtils {

    private FileUtils() {
    }

    public static String getMd5(MultipartFile file) throws IOException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] fileBytes = file.getBytes();
        byte[] digest = md.digest(fileBytes);
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public static String getExtension(MultipartFile file) {
        return FilenameUtils.getExtension(file.getOriginalFilename()).toLowerCase();
    }

    public static String concatPath(String path1, String path2) {
        return path1.concat("\\").concat(path2);
    }

    public static void transferTo(MultipartFile file, String path) throws IOException {
        File destinationFile = new File(path);
        file.transferTo(destinationFile);
    }

    public static File generateTempFile(MultipartFile file) throws IOException {
        File tempFile = File.createTempFile(Objects.requireNonNull(file.getOriginalFilename()), null);
        file.transferTo(tempFile);
        return tempFile;
    }
}
