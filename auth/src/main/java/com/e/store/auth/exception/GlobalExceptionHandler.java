package com.e.store.auth.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.e.store.auth.viewmodel.res.ErrorVm;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({ BadRequestException.class })
    protected ResponseEntity<ErrorVm> handleBadRequestException (BadRequestException exception, WebRequest webRequest) {
        ErrorVm errorVm = new ErrorVm(exception.getMessage(), "BAD REQUEST", HttpStatus.BAD_REQUEST.toString());
        logger.error(errorVm.toString());
        return ResponseEntity.badRequest().body(errorVm);
    }

    @ExceptionHandler({ NotFoundException.class, UsernameNotFoundException.class })
    protected ResponseEntity<ErrorVm> handleNotFoundException (RuntimeException exception, WebRequest webRequest) {
        ErrorVm errorVm = new ErrorVm(exception.getMessage(), "RESOURCE NOT FOUND", HttpStatus.NOT_FOUND.toString());
        logger.error(errorVm.toString());
        return ResponseEntity.status(404).body(errorVm);
    }

    @ExceptionHandler({ TokenException.class })
    protected ResponseEntity<ErrorVm> handleTokenException (TokenException exception, WebRequest webRequest) {
        ErrorVm errorVm = new ErrorVm(exception.getMessage(), "AUTHENTICATE FAILED", HttpStatus.UNAUTHORIZED.toString());
        logger.error(errorVm.toString());
        return ResponseEntity.status(401).body(errorVm);
    }

    @ExceptionHandler({ ForbiddenException.class })
    protected ResponseEntity<ErrorVm> handleTokenException (ForbiddenException exception, WebRequest webRequest) {
        ErrorVm errorVm = new ErrorVm(exception.getMessage(), "AUTHENTICATE FAILED", HttpStatus.FORBIDDEN.toString());
        logger.error(errorVm.toString());
        return ResponseEntity.status(403).body(errorVm);
    }

    @ExceptionHandler({ InternalErrorException.class })
    protected ResponseEntity<ErrorVm> handleInternalErrorException (InternalErrorException exception, WebRequest webRequest) {
        ErrorVm errorVm = new ErrorVm(exception.getMessage(), "INTERNAL ERROR", HttpStatus.INTERNAL_SERVER_ERROR.toString());
        logger.error(errorVm.toString());
        return ResponseEntity.status(501).body(errorVm);
    }
}
