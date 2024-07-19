package ru.golovin.cloud.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> handle(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.ok(213);
    }
}
