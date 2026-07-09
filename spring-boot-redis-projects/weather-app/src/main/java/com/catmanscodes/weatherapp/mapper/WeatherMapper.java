package com.catmanscodes.weatherapp.mapper;

import com.catmanscodes.weatherapp.dto.WeatherDto;
import com.catmanscodes.weatherapp.entity.Weather;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface WeatherMapper {

    //1.  entity to dto

    WeatherDto toWeatherDto(Weather weather);

    // 2. dto to entity

    // id is generated; createdAt/updatedAt are managed by Hibernate timestamps.
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Weather toWeatherEntity(WeatherDto weatherDto);


    // In-place update of an existing managed entity (used by updateWeather).
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateWeatherFromDto(WeatherDto weatherDto, @MappingTarget Weather weather);

}
