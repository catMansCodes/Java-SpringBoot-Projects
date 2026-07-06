package com.catmanscodes.order_service.controller;

import com.catmanscodes.order_service.dto.OrderEvent;
import com.catmanscodes.order_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<String> createOrder(@Valid @RequestBody OrderEvent orderEvent) {

        String orderEventId = orderService.sendOrderEvent(orderEvent);

        return ResponseEntity.ok(orderEventId);
    }

}
