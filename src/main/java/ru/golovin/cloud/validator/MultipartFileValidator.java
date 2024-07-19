package ru.golovin.cloud.validator;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.ValidationException;

@Component
public class MultipartFileValidator implements Validator<MultipartFile> {

    @Override
    public void validate(MultipartFile input) throws ValidationException {

    }
}
