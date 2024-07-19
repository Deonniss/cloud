package ru.golovin.cloud.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.golovin.cloud.entity.File;
import ru.golovin.cloud.service.FileService;

import java.io.InputStream;

@RestController
@RequestMapping(path = "/file", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<File> createContent(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(fileService.upload(file));
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> createContent(@PathVariable String fileName) {
        try {
            InputStream inputStream = fileService.download(fileName);
            InputStreamResource resource = new InputStreamResource(inputStream);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
