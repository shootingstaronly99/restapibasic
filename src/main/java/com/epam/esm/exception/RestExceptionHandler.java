package com.epam.esm.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.NoSuchElementException;


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(HttpServletRequest request) {
        String message = "Requested resource not found (id=" + request.getParameter("id ") + ")";
        int statusCode = HttpStatus.NOT_FOUND.value();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND)
                .statusCode(statusCode)
                .message(message)
                .build();
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Object> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        String message = e.getLocalizedMessage();
        int statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .statusCode(statusCode)
                .message(message)
                .build();
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(IncorrectParameterException.class)
    public ResponseEntity<Object> handleIncorrectParameterException(IncorrectParameterException e) {
        String message = e.getLocalizedMessage();
        int statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .statusCode(statusCode)
                .message(message)
                .build();
        return buildResponseEntity(errorResponse);
    }

    @ExceptionHandler(GiftCertificateException.class)
    public ResponseEntity<Object> handleNullPointerException(GiftCertificateException e) {
        String message = e.getLocalizedMessage();
        int statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .statusCode(statusCode)
                .message(message)
                .build();
        return buildResponseEntity(errorResponse);
    }

    public ResponseEntity<Object> buildResponseEntity(ErrorResponse errorResponse) {
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }
}
