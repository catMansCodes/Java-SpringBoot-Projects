package com.catmanscodes.rabbitmq.service;

import com.catmanscodes.rabbitmq.dto.Employee;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RabbitMQProducerService {

    private final Logger logger = LoggerFactory.getLogger(RabbitMQProducerService.class);

    public final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.binding.routing.key}")
    private String routingKey;

    @Value("${rabbitmq.employee.binding.routing.key}")
    private String routingEmployeeKey;


    public void sendMessage(String message) {

        logger.info("Sending Message: ...." + message);

        rabbitTemplate.convertAndSend(exchangeName, routingKey, message);

    }

    public void sendEmployeeMessage(Employee employee) {

        logger.info("Sending Employee: ...." + employee);

        rabbitTemplate.convertAndSend(exchangeName, routingEmployeeKey, employee);
    }


}
