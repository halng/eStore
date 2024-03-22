package com.e.store.search.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ProductConsumer {

  private static final Logger LOGGER = LoggerFactory.getLogger(ProductConsumer.class);

  private final String dbProduct = "db.product-new";
  private final String groupId = "search-consumer";

  @KafkaListener(
      topics = dbProduct,
      groupId = groupId,
      containerFactory = "kafkaListenerContainerFactory")
  public void listenNewProduct(String msg) {
    LOGGER.info(msg);
  }
}
