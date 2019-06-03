package com.kanadem.rabbitmqtopicprovider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitMqTopicProviderApplication.class)
public class RabbitMqTopicProviderApplicationTests {
    @Autowired
    private UserSender usersender;
    @Autowired
    private ProductSender productsender;
    @Autowired
    private OrderSender ordersender;

    @Test
    public void send() throws InterruptedException{
        while(true){
            Thread.sleep(3000);
            this.usersender.send();
            this.productsender.send();
            this.ordersender.send();
        }
    }
}

