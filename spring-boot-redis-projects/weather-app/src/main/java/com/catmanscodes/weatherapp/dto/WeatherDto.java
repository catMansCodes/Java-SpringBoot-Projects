package com.catmanscodes.weatherapp.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record WeatherDto(

        Long id,

        @NotBlank(message = "city is required")
        @Size(max = 100, message = "city must be at most 100 characters")
        String city,

        @NotBlank(message = "details is required")
        @Size(max = 255, message = "details must be at most 255 characters")
        String details,

        @NotNull(message = "temperature is required")
        Double temperature,

        @NotNull(message = "humidity is required")
        @Min(value = 0, message = "humidity must be between 0 and 100")
        @Max(value = 100, message = "humidity must be between 0 and 100")
        Integer humidity,

        LocalDateTime createdAt,

        LocalDateTime updatedAt) {
}
