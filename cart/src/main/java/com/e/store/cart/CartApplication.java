package com.e.store.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CartApplication {

  public String hello() {
    return "Hello To The World!";
  }

  public static void main(String[] args) {
    SpringApplication.run(CartApplication.class, args);
  }
}
