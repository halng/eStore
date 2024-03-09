package com.e.store.email.listener;

import com.e.store.email.service.EmailService;
import com.e.store.email.viewmodel.res.AuthMessageVm;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageListener {

  private static final Logger log = LoggerFactory.getLogger(KafkaMessageListener.class);

  @Autowired private EmailService emailService;

  @KafkaListener(
      topics = "user_register",
      groupId = "user",
      containerFactory = "kafkaListenerContainerFactory")
  public void listenGroupUser(String message) {
    log.info("Get new message: {}", message);
    JsonObject json = JsonParser.parseString(message).getAsJsonObject();
    AuthMessageVm authMessageVm = AuthMessageVm.fromJsonObject(json);
    emailService.sendEmail(authMessageVm);
  }
}
