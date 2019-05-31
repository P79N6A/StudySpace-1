package com.kanadem.rabbitmqtopicconsumer;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(bindings =  @QueueBinding(
        value = @Queue(value = "${mq.config.queue.info}", autoDelete = "true"),
        exchange = @Exchange(value = "${mq.config.exchange}", type = ExchangeTypes.TOPIC),
        key = "*.log.info"
)
)
public class InfoReceiver {
    @RabbitHandler
    public void process(String msg){
        System.out.println("收到Info日志: " + msg);
    }
}
