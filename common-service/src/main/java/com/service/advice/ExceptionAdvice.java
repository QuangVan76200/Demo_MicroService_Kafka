package com.service.advice;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.service.common.ErrorMessage;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionAdvice {
	
	@ExceptionHandler
	public ResponseEntity<ErrorMessage> handleException(Exception ex) {
		log.error("Unkown internal server error " + ex.getMessage());
		log.error("Exception class " + ex.getClass());
		log.error("Exception cause " + ex.getCause());

		return new ResponseEntity(new ErrorMessage("99999", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR),
				HttpStatus.INTERNAL_SERVER_ERROR);

	}

}
