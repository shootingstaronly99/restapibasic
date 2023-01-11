package com.epam.esm.exception;

public class IncorrectParameterException extends Exception {
    public IncorrectParameterException() {
    }

    public IncorrectParameterException(String messageCode) {
        super(messageCode);
    }

    public IncorrectParameterException(String messageCode, Throwable cause) {
        super(messageCode, cause);
    }

    public IncorrectParameterException(Throwable cause) {
        super(cause);
    }
}