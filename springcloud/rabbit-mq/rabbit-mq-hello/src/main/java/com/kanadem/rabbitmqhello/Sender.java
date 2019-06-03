package com.kanadem.rabbitmqhello;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Sender {
    @Autowired
    private AmqpTemplate rabbitTemplate;
    public void send(){
        String msg = "Hello" + new Date();
        this.rabbitTemplate.convertAndSend("Hello-kanadem-queue", msg);
    }
}
