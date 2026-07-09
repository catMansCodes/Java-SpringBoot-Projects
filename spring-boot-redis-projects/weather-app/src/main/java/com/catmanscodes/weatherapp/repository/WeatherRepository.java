package com.catmanscodes.weatherapp.repository;

import com.catmanscodes.weatherapp.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {

    Optional<Weather> findByCity(String city);

    boolean existsByCity(String city);

    boolean existsByCityAndIdNot(String city, Long id);

}
