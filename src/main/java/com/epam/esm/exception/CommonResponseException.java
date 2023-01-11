package com.epam.esm.exception;

import org.springframework.http.HttpStatus;

public class CommonResponseException extends RuntimeException {
    private HttpStatus httpStatus;

    public CommonResponseException(String key, HttpStatus httpStatus) {
        super(key);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public CommonResponseException setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }
}