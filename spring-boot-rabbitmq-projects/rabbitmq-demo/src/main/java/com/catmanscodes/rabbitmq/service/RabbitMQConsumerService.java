package com.catmanscodes.rabbitmq.service;

import com.catmanscodes.rabbitmq.dto.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumerService {

    private final Logger logger = LoggerFactory.getLogger(RabbitMQConsumerService.class);

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void receiveMessage(String message) {

        logger.info("Received Message ...: " + message);

    }

    @RabbitListener(queues = "${rabbitmq.queue.employee.name}")
    public void receiveEmployeeMessage(Employee employee) {
        logger.info("Received Employee Message ...: " + employee.toString());
    }

}
