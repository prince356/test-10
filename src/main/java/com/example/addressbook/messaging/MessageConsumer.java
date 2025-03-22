package com.example.addressbook.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageConsumer {
    @RabbitListener(queues = "addressbook.queue")
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
    }
}
