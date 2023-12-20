package com.e.store.auth.exception;

import com.e.store.auth.viewmodel.res.ErrorVm;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler({BadRequestException.class, IllegalArgumentException.class})
  protected ResponseEntity<ErrorVm> handleBadRequestException(
      Exception exception, WebRequest webRequest) {
    ErrorVm errorVm =
        new ErrorVm(exception.getMessage(), "BAD REQUEST", HttpStatus.BAD_REQUEST.toString());
    log.error("Bad request: {}", exception.getMessage());
    return ResponseEntity.badRequest().body(errorVm);
  }

  @ExceptionHandler({NotFoundException.class, UsernameNotFoundException.class})
  protected ResponseEntity<ErrorVm> handleNotFoundException(
      RuntimeException exception, WebRequest webRequest) {
    ErrorVm errorVm =
        new ErrorVm(exception.getMessage(), "RESOURCE NOT FOUND", HttpStatus.NOT_FOUND.toString());
    log.error("Not found: {}", exception.getMessage());
    return ResponseEntity.status(404).body(errorVm);
  }

  @ExceptionHandler({
    TokenException.class,
    SignatureException.class,
    MalformedJwtException.class,
    ExpiredJwtException.class,
    UnsupportedJwtException.class
  })
  protected ResponseEntity<ErrorVm> handleTokenException(
      Exception exception, WebRequest webRequest) {
    ErrorVm errorVm =
        new ErrorVm(
            exception.getMessage(), "AUTHENTICATE FAILED", HttpStatus.UNAUTHORIZED.toString());
    log.error(
        "Token exception: authentication failed. Try again!. Detail: {}", exception.getMessage());
    return ResponseEntity.status(401).body(errorVm);
  }

  @ExceptionHandler({ForbiddenException.class})
  protected ResponseEntity<ErrorVm> handleTokenException(
      ForbiddenException exception, WebRequest webRequest) {
    ErrorVm errorVm =
        new ErrorVm(exception.getMessage(), "ACCESS DENIED", HttpStatus.FORBIDDEN.toString());
    log.error("ForbiddenException: {}", exception.getMessage());
    return ResponseEntity.status(403).body(errorVm);
  }

  @ExceptionHandler({InternalErrorException.class})
  protected ResponseEntity<ErrorVm> handleInternalErrorException(
      InternalErrorException exception, WebRequest webRequest) {
    ErrorVm errorVm =
        new ErrorVm(
            exception.getMessage(), "INTERNAL ERROR", HttpStatus.INTERNAL_SERVER_ERROR.toString());
    log.error("InternalErrorException: {}", exception.getMessage());
    return ResponseEntity.status(501).body(errorVm);
  }

  @ExceptionHandler({AuthenException.class})
  protected ResponseEntity<ErrorVm> handleAuthenticationException(
      AuthenException e, WebRequest webRequest) {
    ErrorVm errorVm =
        new ErrorVm(e.getMessage(), "BAD CREDENTIAL", HttpStatus.UNAUTHORIZED.toString());
    log.error("AuthenException {}", e.getMessage());
    return ResponseEntity.status(401).body(errorVm);
  }
}
