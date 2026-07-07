package com.catmanscodes.smsservice.service;

import com.catmanscodes.smsservice.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class SMSService {

    private final Logger logger = LoggerFactory.getLogger(SMSService.class);

    @RabbitListener(queues = "${rabbitmq.sms.queue.name}")
    public void getSMS(UserDto userDto) {
        logger.info(String.format("inside getSMS(): %s", userDto.toString()));
    }

}
