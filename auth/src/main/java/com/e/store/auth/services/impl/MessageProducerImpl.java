package com.e.store.auth.services.impl;

import com.e.store.auth.exception.InternalErrorException;
import com.e.store.auth.services.IMessageProducer;
import com.e.store.auth.viewmodel.res.AuthMessageVm;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageProducerImpl implements IMessageProducer {

    @Value("${kafka.topic}")
    private String topicName;

    @Autowired
    private final KafkaTemplate<String, AuthMessageVm> kafkaTemplate;

    @Override
    public void sendMessage(AuthMessageVm authMessageVm) {
        CompletableFuture<SendResult<String, AuthMessageVm>> future = kafkaTemplate.send(topicName, authMessageVm);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                throw new InternalErrorException("Can not send email for activation! Try again");
            }
        });

    }
}
