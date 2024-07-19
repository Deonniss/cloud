package ru.golovin.cloud.service.minio;

import io.minio.*;
import io.minio.errors.MinioException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.golovin.cloud.util.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Service
public class MinIOFileStorageService {

    private final MinioClient minioClient;
    private final String bucketName;

    @SneakyThrows
    public MinIOFileStorageService(
            @Value("${minio.url}") String minioUrl,
            @Value("${minio.access-key}") String minioAccessKey,
            @Value("${minio.secret-key}") String minioSecretKey,
            @Value("${minio.bucket-name}") String bucketName
    ) {
        this.minioClient = MinioClient.builder()
                .endpoint(minioUrl)
                .credentials(minioAccessKey, minioSecretKey)
                .build();
        this.bucketName = bucketName;
        BucketExistsArgs bucket = BucketExistsArgs.builder().bucket(bucketName).build();
        if (!minioClient.bucketExists(bucket)) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }

    @SneakyThrows
    public void uploadFile(MultipartFile file) {
        File tempFile = FileUtils.generateTempFile(file);
        try (InputStream fileStream = new FileInputStream(tempFile)) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(file.getOriginalFilename())
                            .stream(fileStream, tempFile.length(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        }
    }

    @SneakyThrows
    public InputStream downloadFile(String fileName) {
        try {

            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .build()
            );
        } catch (Throwable e) {
            throw new MinioException("Error downloading file: " + fileName);
        }
    }
}
