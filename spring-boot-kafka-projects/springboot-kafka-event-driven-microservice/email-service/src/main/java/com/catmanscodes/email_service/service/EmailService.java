package com.catmanscodes.email_service.service;

import com.catmanscodes.email_service.dto.OrderEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailService {

    private final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @KafkaListener(
            topics = "${spring.kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void receiveOrderEvent(OrderEvent orderEvent) {
        logger.info("Received order event {} for customer {}",
                orderEvent.getOrderEventId(), orderEvent.getCustomerId());

        //next step ..
    }
}
