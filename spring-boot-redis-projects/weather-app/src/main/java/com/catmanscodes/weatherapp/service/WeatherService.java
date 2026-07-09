package com.catmanscodes.weatherapp.service;

import com.catmanscodes.weatherapp.dto.WeatherDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WeatherService {

    Page<WeatherDto> findAll(Pageable pageable);

    WeatherDto findById(Long id);

    WeatherDto findByCity(String city);

    WeatherDto addWeather(WeatherDto weatherDto);

    WeatherDto updateWeather(WeatherDto weatherDto, Long id);

    void deleteById(Long id);

}
