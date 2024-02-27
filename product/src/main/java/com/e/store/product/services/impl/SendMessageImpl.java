package com.e.store.product.services.impl;

import com.e.store.product.services.SendMessage;
import com.google.gson.Gson;
import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Service
public class SendMessageImpl implements SendMessage {
  private static final Logger LOGGER = LoggerFactory.getLogger(SendMessageImpl.class);

  private static final String NEW_PRODUCT_TOPIC = "product-new";
  private final KafkaTemplate<String, String> kafkaTemplate;

  @Autowired
  public SendMessageImpl(KafkaTemplate<String, String> k) {
    kafkaTemplate = k;
  }

  @Override
  public <T> void putMsg(T data) {
    LOGGER.info("Start put message {} into {} topic.", data.toString(), NEW_PRODUCT_TOPIC);

    String stringData = new Gson().toJson(data);

    CompletableFuture<SendResult<String, String>> future =
        kafkaTemplate.send(NEW_PRODUCT_TOPIC, stringData);

    future.whenComplete(
        (res, ex) -> {
          if (ex == null) {
            throw new InternalError(
                "Can not put message into topic %s with error: %s"
                    .formatted(NEW_PRODUCT_TOPIC, ex.getMessage()));
          }
        });
  }
}
