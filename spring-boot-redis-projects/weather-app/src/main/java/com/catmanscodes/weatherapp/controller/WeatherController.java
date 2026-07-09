package com.catmanscodes.weatherapp.controller;

import com.catmanscodes.weatherapp.dto.WeatherDto;
import com.catmanscodes.weatherapp.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/weather")
@Tag(
        name = "Weather",
        description = "CRUD operations for weather records"
)
public class WeatherController {

    private final WeatherService weatherService;

    @Operation(summary = "List weather records (paged)")
    @GetMapping
    public ResponseEntity<Page<WeatherDto>> findAll(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(weatherService.findAll(pageable));
    }

    @Operation(summary = "Get a weather record by id")
    @GetMapping("/{id}")
    public ResponseEntity<WeatherDto> findById(@PathVariable long id) {
        return ResponseEntity.ok(weatherService.findById(id));
    }

    @Operation(summary = "Get a weather record by city")
    @GetMapping("/city/{city}")
    public ResponseEntity<WeatherDto> findByCity(@PathVariable String city) {
        return ResponseEntity.ok(weatherService.findByCity(city));
    }

    @Operation(summary = "Create a new weather record")
    @PostMapping("/create")
    public ResponseEntity<WeatherDto> save(@Valid @RequestBody WeatherDto weatherDto) {
        return new ResponseEntity<>(weatherService.addWeather(weatherDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing weather record")
    @PutMapping("/{id}")
    public ResponseEntity<WeatherDto> update(@Valid @RequestBody WeatherDto weatherDto, @PathVariable long id) {
        return ResponseEntity.ok(weatherService.updateWeather(weatherDto, id));
    }

    @Operation(summary = "Delete a weather record by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        weatherService.deleteById(id);
        return ResponseEntity.ok("deleted");
    }
}
