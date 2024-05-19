package com.e.store.product.viewmodel.res;

public record ErrorResVm(String message, int statusCode, String reason) {

  public String getLogMessage() {
    return "Error Message: " + this.message + "\nStatus Code: " + this.statusCode;
  }
}
