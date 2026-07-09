package com.catmanscodes.weatherapp.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        String statusCode,
        String errorMessage,
        LocalDateTime timestamp,
        Map<String, String> validationErrors) {

    // Convenience constructor for errors without per-field validation details.
    public ErrorResponse(String statusCode, String errorMessage, LocalDateTime timestamp) {
        this(statusCode, errorMessage, timestamp, null);
    }
}
