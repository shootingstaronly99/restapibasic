package com.epam.esm.controller.response;

public final class ResponseMessage {
    private ResponseMessage() {
    }

    public static final String SUCCESSFULLY_CREATED = "Successfully created data!";
    public static final String SUCCESSFULLY_RECEIVED = "Successfully received data!";
    public static final String SUCCESSFULLY_UPDATED = "Successfully updated GiftCertificate with id ";
    public static final String SUCCESSFULLY_DELETED = "Successfully deleted GiftCertificate with id ";
    public static final String SUCCESSFULLY_DELETED_TAG = "Successfully deleted Tag with id ";
    public static final String UPDATE_ERROR = "Error occurred while updating Gift Certificate with id ";
    public static final String DELETE_ERROR = "Error occurred while deleting Gift Certificate with id ";
}