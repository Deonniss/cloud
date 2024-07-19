package ru.golovin.cloud.validator;

import javax.xml.bind.ValidationException;

public interface Validator<E> {

    void validate(E input) throws ValidationException;
}
