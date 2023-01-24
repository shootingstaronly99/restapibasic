package com.epam.esm;

import lombok.Builder;
import lombok.Data;

@Data
public class ResponseModel<T> {

    private T data;
    private String message;
    private int statusCode; // 200  success, the others error

    public ResponseModel(T data) {
        this.data = data;
    }
}
