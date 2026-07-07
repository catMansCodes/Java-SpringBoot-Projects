package com.catmanscodes.emailservice.service;

import com.catmanscodes.emailservice.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @RabbitListener(queues = "${rabbitmq.email.queue.name}")
    public void receiveEmail(UserDto userDto) {

        logger.info(String.format("Received email from sms queue: %s", userDto.toString()));

    }

}
