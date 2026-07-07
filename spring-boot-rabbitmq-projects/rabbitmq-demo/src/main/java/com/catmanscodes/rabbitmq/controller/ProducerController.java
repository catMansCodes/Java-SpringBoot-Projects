package com.catmanscodes.rabbitmq.controller;

import com.catmanscodes.rabbitmq.dto.Employee;
import com.catmanscodes.rabbitmq.service.RabbitMQProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
public class ProducerController {

    private final RabbitMQProducerService rabbitMQProducerService;

    @GetMapping("/publish")
    public ResponseEntity<String> sendMessage(@RequestParam String message) {
        try {
            rabbitMQProducerService.sendMessage(message);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return ResponseEntity.ok("Message sent successfully ");
    }


    @PostMapping("/publish")
    public ResponseEntity<Employee> sendMessage(@RequestBody Employee employee) {
        try {

            employee.setId(UUID.randomUUID().toString());

            rabbitMQProducerService.sendEmployeeMessage(employee);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return ResponseEntity.ok(employee);
    }


}
