package com.epam.esm.exception;


import org.springframework.http.HttpStatus;

public class TagException extends CommonResponseException {

    public TagException(String key) {
        super(key, HttpStatus.NO_CONTENT);
    }
}
