package com.epam.esm.exception;

import org.springframework.http.HttpStatus;

public class GiftCertificateException extends CommonResponseException {

    public GiftCertificateException(String key) {
        super(key, HttpStatus.NO_CONTENT);
    }
}
