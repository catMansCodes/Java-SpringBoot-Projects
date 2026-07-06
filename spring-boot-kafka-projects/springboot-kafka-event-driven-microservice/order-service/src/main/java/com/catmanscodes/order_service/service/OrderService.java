package com.catmanscodes.order_service.service;

import com.catmanscodes.order_service.dto.OrderEvent;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final NewTopic newTopic;
    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public String sendOrderEvent(OrderEvent orderEvent) {

        orderEvent.setOrderEventId(UUID.randomUUID().toString());
        orderEvent.setOrderDate(Instant.now().toString());

        logger.info("Sending order event {} to Kafka topic {}", orderEvent.getOrderEventId(), newTopic.name());

        kafkaTemplate.send(newTopic.name(), orderEvent.getOrderEventId(), orderEvent)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        logger.error("Failed to send order event {}", orderEvent.getOrderEventId(), ex);
                    } else {
                        logger.info("Sent order event {} to partition {}",
                                orderEvent.getOrderEventId(), result.getRecordMetadata().partition());
                    }
                });

        return orderEvent.getOrderEventId();
    }

}
