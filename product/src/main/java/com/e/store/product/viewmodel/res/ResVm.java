package com.e.store.product.viewmodel.res;

import org.springframework.http.HttpStatus;

public record ResVm(HttpStatus status, String message) {
  public String getLogMessage() {
    return status.toString() + " - " + message;
  }
}
