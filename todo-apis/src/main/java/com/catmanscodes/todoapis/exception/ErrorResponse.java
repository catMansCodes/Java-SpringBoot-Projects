package com.catmanscodes.todoapis.exception;

import java.time.LocalDateTime;


public record ErrorResponse(

        String errorCode,
        String errorMessage,
        LocalDateTime timestamp,
        String details) {
}
