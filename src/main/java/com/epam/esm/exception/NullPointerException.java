package com.epam.esm.exception;

public class NullPointerException extends Exception {
    public NullPointerException() {
    }

    public NullPointerException(String messageCode) {
        super(messageCode);
    }

    public NullPointerException(String messageCode, Throwable cause) {
        super(messageCode, cause);
    }

    public NullPointerException(Throwable cause) {
        super(cause);
    }
}
