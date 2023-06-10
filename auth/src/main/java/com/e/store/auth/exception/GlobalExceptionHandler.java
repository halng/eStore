package com.e.store.auth.exception;

import com.e.store.auth.viewmodel.res.ErrorVm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({BadRequestException.class})
    protected ResponseEntity<ErrorVm> handleBadRequestException(BadRequestException exception, WebRequest webRequest) {
        ErrorVm errorVm = new ErrorVm(exception.getMessage(),"BAD REQUEST", HttpStatus.BAD_REQUEST.toString());
        return ResponseEntity.badRequest().body(errorVm);
    }
}
