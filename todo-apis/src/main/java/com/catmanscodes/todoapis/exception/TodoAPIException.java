package com.catmanscodes.todoapis.exception;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;


@AllArgsConstructor
public class TodoAPIException extends RuntimeException {
    private String message;
    private HttpStatus status;
}
