package com.epam.esm.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class ErrorResponse {
    private HttpStatus status;
    private String message;
    private int statusCode;
}