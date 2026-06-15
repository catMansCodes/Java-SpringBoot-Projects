package com.catmanscodes.userapis.exception;

import java.time.LocalDateTime;

public record ErrorResponse(String statusCode, String errorMessage, LocalDateTime timestamp) {
}
