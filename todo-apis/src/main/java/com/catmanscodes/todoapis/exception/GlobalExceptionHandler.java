package com.catmanscodes.todoapis.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(
                "NOT_FOUND",
                e.getMessage(),
                LocalDateTime.now(),
                request.getDescription(false)
        ), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception e, WebRequest request) {
        return new ResponseEntity<>(new ErrorResponse(
                "INTERNAL SERVER ERROR",
                e.getMessage(),
                LocalDateTime.now(),
                request.getDescription(false)
        ), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
