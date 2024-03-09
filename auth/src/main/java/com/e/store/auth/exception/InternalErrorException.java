package com.e.store.auth.exception;

public class InternalErrorException extends RuntimeException {

  public InternalErrorException() {
    super();
  }

  public InternalErrorException(String msg) {
    super(msg);
  }
}
