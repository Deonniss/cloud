package ru.golovin.cloud.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.golovin.cloud.entity.File;
import ru.golovin.cloud.repository.FileRepository;
import ru.golovin.cloud.service.minio.MinIOFileStorageService;
import ru.golovin.cloud.util.FileUtils;
import ru.golovin.cloud.validator.MultipartFileValidator;

import java.io.InputStream;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileService {

    private final MinIOFileStorageService minIOFileStorageService;
    private final MultipartFileValidator multipartFileValidator;
    private final FileRepository fileRepository;

    @SneakyThrows
    @Transactional
    public File upload(@NotNull MultipartFile file) {
        multipartFileValidator.validate(file);
        return saveFile(file);
    }

    @SneakyThrows
    private File saveFile(@NotNull MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String md5 = FileUtils.getMd5(file);
        File entity = new File();
        entity.setName(fileName);
        entity.setPath("1");
        entity.setExtension(FileUtils.getExtension(file));
        entity.setWeight(file.getSize());
        entity.setMd5(md5);
        fileRepository.save(entity);
        minIOFileStorageService.uploadFile(file);
        return entity;
    }

    @SneakyThrows
    public InputStream download(String fileName) {
        InputStream inputStream = minIOFileStorageService.downloadFile(fileName);
        return inputStream;
    }
}
