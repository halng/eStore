package com.e.store.product.exceptions;

import com.e.store.product.viewmodel.res.ErrorResVm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler({ EntityNotFoundException.class })
	public ResponseEntity<ErrorResVm> entityNotFoundExceptionHandler(EntityNotFoundException entityNotFoundException,
			WebRequest webRequest) {
		ErrorResVm errorResVm = new ErrorResVm(entityNotFoundException.getMessage(), HttpStatus.NOT_FOUND.value(), "");
		LOG.error(errorResVm.getLogMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResVm);
	}

	@ExceptionHandler({ BadRequestException.class })
	public ResponseEntity<ErrorResVm> badRequestExceptionHandler(BadRequestException badRequestException,
			WebRequest webRequest) {
		ErrorResVm errorResVm = new ErrorResVm(badRequestException.getMessage(), HttpStatus.BAD_REQUEST.value(), "");
		LOG.error(errorResVm.getLogMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResVm);
	}

}
