package com.kanadem.rabbitmqdirectprovider;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RabbitMqDirectProviderApplication.class)
public class RabbitMqDirectProviderApplicationTests {
    @Autowired
    private Sender sender;

    @Test
    public void send() throws InterruptedException{
        while(true){
            Thread.sleep(3000);
            this.sender.send();
        }
    }
}

