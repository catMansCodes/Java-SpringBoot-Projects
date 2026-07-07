package com.catmanscodes.userservice.service;

import com.catmanscodes.userservice.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.sms.binding.routing.key}")
    private String smsRoutingKey;

    @Value("${rabbitmq.email.binding.routing.key}")
    private String emailRoutingKey;

    public UserDto sendEmail(UserDto userDto) {
        logger.info("sendEmail service called...");

        rabbitTemplate.convertAndSend(exchangeName, emailRoutingKey, userDto);

        return userDto;
    }

    public UserDto sendSMS(UserDto userDto) {
        logger.info("sendSMS service called...");

        rabbitTemplate.convertAndSend(exchangeName, smsRoutingKey, userDto);

        return userDto;
    }

}
