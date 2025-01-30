package com.example.my_app;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @RabbitListener(queues = "spring-boot3", ackMode = "AUTO")
    public void receiveMessage(String message) {
        System.out.println("Primljena poruka: " + message);
    }
}