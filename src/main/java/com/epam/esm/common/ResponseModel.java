package com.epam.esm.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseModel<T> {
    private T data;
    private String message;
    private int statusCode;

    public ResponseModel(T data) {
        this.data = data;
        this.message = "";
        this.statusCode = 200;
    }

    public ResponseModel(T data, String message) {
        this.data = data;
        this.message = message;
    }
    public ResponseModel() {
        this.message = "";
    }

}
