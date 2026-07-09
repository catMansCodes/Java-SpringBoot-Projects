package com.catmanscodes.weatherapp.service;

import com.catmanscodes.weatherapp.dto.WeatherDto;
import com.catmanscodes.weatherapp.entity.Weather;
import com.catmanscodes.weatherapp.exception.WeatherAlreadyExistsException;
import com.catmanscodes.weatherapp.exception.WeatherNotFoundException;
import com.catmanscodes.weatherapp.mapper.WeatherMapper;
import com.catmanscodes.weatherapp.repository.WeatherRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class WeatherServiceImpl implements WeatherService {

    private static final Logger logger = LoggerFactory.getLogger(WeatherServiceImpl.class);

    private final WeatherRepository weatherRepository;
    private final WeatherMapper weatherMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<WeatherDto> findAll(Pageable pageable) {

        return weatherRepository.findAll(pageable)
                .map(weatherMapper::toWeatherDto);
    }

    @Override
    @Cacheable(cacheNames = "weatherById", key = "#id")
    @Transactional(readOnly = true)
    public WeatherDto findById(Long id) {
        Weather weather = weatherRepository.findById(id)
                .orElseThrow(() -> new WeatherNotFoundException("Weather not found with id: " + id));

        return weatherMapper.toWeatherDto(weather);
    }

    @Override
    @Cacheable(cacheNames = "weatherByCity", key = "#city")
    @Transactional(readOnly = true)
    public WeatherDto findByCity(String city) {

        Weather weather = weatherRepository.findByCity(city)
                .orElseThrow(() -> new WeatherNotFoundException("Weather not found for city: " + city));

        return weatherMapper.toWeatherDto(weather);
    }

    @Override
    @CacheEvict(cacheNames = "weatherByCity", allEntries = true)
    @Transactional
    public WeatherDto addWeather(WeatherDto weatherDto) {

        if (weatherRepository.existsByCity(weatherDto.city())) {
            throw new WeatherAlreadyExistsException("Weather already exists for city: " + weatherDto.city());
        }

        Weather weather = weatherMapper.toWeatherEntity(weatherDto);

        Weather saved = weatherRepository.save(weather);

        logger.info("Created weather record id={} for city={}", saved.getId(), saved.getCity());

        return weatherMapper.toWeatherDto(saved);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "weatherById", key = "#id"),
            @CacheEvict(cacheNames = "weatherByCity", allEntries = true)
    })
    @Transactional
    public WeatherDto updateWeather(WeatherDto weatherDto, Long id) {
        Weather weather = weatherRepository.findById(id)
                .orElseThrow(() -> new WeatherNotFoundException("Weather not found with id: " + id));

        if (weatherRepository.existsByCityAndIdNot(weatherDto.city(), id)) {
            throw new WeatherAlreadyExistsException("Weather already exists for city: " + weatherDto.city());
        }

        // DTO -> Entity i.e weather.setCity(weatherDto.getCity) ... like other all
        weatherMapper.updateWeatherFromDto(weatherDto, weather);

        // saveAndFlush forces the flush so @UpdateTimestamp regenerates updatedAt
        // before we map the entity back to a DTO (otherwise the response is stale).

        Weather saved = weatherRepository.saveAndFlush(weather);

        logger.info("Updated weather record id={}", saved.getId());

        return weatherMapper.toWeatherDto(saved);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "weatherById", key = "#id"),
            @CacheEvict(cacheNames = "weatherByCity", allEntries = true)
    })
    @Transactional
    public void deleteById(Long id) {
        if (!weatherRepository.existsById(id)) {
            throw new WeatherNotFoundException("Weather not found with id: " + id);
        }

        weatherRepository.deleteById(id);
        
        logger.info("Deleted weather record id={}", id);
    }
}
