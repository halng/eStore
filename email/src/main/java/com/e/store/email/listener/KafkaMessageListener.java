package com.e.store.email.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class KafkaMessageListener {
    @KafkaListener(topics = "user-register", groupId = "user")
    public void listenGroupUser(String message) {
        System.out.println("Received message " + message);
    }
}
