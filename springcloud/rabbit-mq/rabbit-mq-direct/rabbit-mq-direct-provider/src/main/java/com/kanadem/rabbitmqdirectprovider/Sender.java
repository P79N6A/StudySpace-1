package com.kanadem.rabbitmqdirectprovider;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Sender {
    @Autowired
    private AmqpTemplate rabbitTemplate;
    @Value("${mq.config.exchange}")
    private String exchange;


    public void send(){
        String msg = "Hello" + new Date();
        this.rabbitTemplate.convertAndSend(this.exchange, "log.info.routing.key", msg);
    }
}
