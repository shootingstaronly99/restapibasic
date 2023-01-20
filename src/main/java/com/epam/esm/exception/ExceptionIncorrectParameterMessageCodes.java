package com.epam.esm.exception;

public enum ExceptionIncorrectParameterMessageCodes {
    BAD_GIFT_CERTIFICATE_NAME("INCORRECT  GIFT CERTIFICATE NAME"),

    BAD_GIFT_CERTIFICATE_DESCRIPTION("INCORRECT GIFT CERTIFICATE DESCRIPTION"),
    BAD_GIFT_CERTIFICATE_PRICE("INCORRECT GIFT CERTIFICATE PRICE"),

    BAD_GIFT_CERTIFICATE_DURATION("INCORRECT GIFT CERTIFICATE DURATION");
    private final String s;

    ExceptionIncorrectParameterMessageCodes(String s) {
        this.s = s;
    }


}
