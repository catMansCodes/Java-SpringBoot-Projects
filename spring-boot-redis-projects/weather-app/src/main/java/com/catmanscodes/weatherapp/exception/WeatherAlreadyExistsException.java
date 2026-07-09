package com.catmanscodes.weatherapp.exception;

public class WeatherAlreadyExistsException extends RuntimeException {

    public WeatherAlreadyExistsException(String message) {
        super(message);
    }

}
