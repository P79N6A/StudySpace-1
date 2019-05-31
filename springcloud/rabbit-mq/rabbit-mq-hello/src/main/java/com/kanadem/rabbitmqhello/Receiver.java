package com.kanadem.rabbitmqhello;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    @RabbitListener(queues = "Hello-kanadem-queue")
    public void process(String msg){
        System.out.println("Receiver: "+msg);
    }
}
