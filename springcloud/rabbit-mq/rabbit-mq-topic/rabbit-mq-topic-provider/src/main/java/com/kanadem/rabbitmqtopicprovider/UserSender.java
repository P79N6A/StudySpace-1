package com.kanadem.rabbitmqtopicprovider;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserSender {
    @Autowired
    private AmqpTemplate rabbitTemplate;
    @Value("${mq.config.exchange}")
    private String exchange;


    public void send() throws InterruptedException{
        this.rabbitTemplate.convertAndSend(this.exchange, "user.log.info", "user.log.info......");
        this.rabbitTemplate.convertAndSend(this.exchange, "user.log.debug", "user.log.debug......");
        this.rabbitTemplate.convertAndSend(this.exchange, "user.log.warning", "user.log.warning......");
        this.rabbitTemplate.convertAndSend(this.exchange, "user.log.error", "user.log.error......");
    }
}
