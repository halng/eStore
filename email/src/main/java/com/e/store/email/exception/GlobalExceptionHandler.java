package com.e.store.email.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private static final Logger loggerFactory = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler({ActiveAccountException.class})
  protected void handleActiveAccountException(ActiveAccountException exception, WebRequest req) {
    loggerFactory.error(exception.getMessage());
  }

  @ExceptionHandler({ActiveAccountTimeOutException.class})
  protected void handleActiveAccountTimeOutException(
      ActiveAccountTimeOutException exception, WebRequest req) {
    loggerFactory.error(exception.getMessage());
  }

  @ExceptionHandler({ParseMessageException.class})
  protected void handleParseMessageException(ParseMessageException exception, WebRequest req) {
    loggerFactory.error(exception.getMessage());
  }
}
