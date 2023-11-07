package com.e.store.auth.services.impl;

import com.e.store.auth.exception.InternalErrorException;
import com.e.store.auth.services.IMessageProducer;
import com.e.store.auth.viewmodel.res.AuthMessageVm;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageProducerImpl implements IMessageProducer {

  private static final Logger LOGGER = LoggerFactory.getLogger(MessageProducerImpl.class);
  @Autowired private final KafkaTemplate<String, String> kafkaTemplate;

  @Value("${kafka.topic}")
  private String topicName;

  @Override
  public void sendMessage(AuthMessageVm authMessageVm) {
    LOGGER.info(
        "Start send message to kafka topic to active account: {}", authMessageVm.username());
    CompletableFuture<SendResult<String, String>> future =
        kafkaTemplate.send(topicName, authMessageVm.toStringWithJsonFormat());

    future.whenComplete(
        (result, ex) -> {
          if (ex == null) {
            throw new InternalErrorException("Can not send email for activation! Try again");
          }
        });
  }
}
