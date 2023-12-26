package com.service.advice;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.service.common.CommonException;
import com.service.common.ErrorMessage;
import com.service.common.ResourceNotFoundException;
import com.service.common.ValidateException;

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

	@ExceptionHandler
	public ResponseEntity<ErrorMessage> handleCommonException(CommonException ex) {
		log.error(String.format("Common error: %s %s %s", ex.getCode(), ex.getStatus(), ex.getMessage()));
		return new ResponseEntity(new ErrorMessage(ex.getCode(), ex.getMessage(), ex.getStatus()), ex.getStatus());
	}

	@ExceptionHandler
	public ResponseEntity<ErrorMessage> handleResouceNotFoundException(ResourceNotFoundException ex) {
		log.error(String.format("Common error: %s %s %s", ex.getResourceName(), ex.getFieldName(), ex.getFieldValue()));
		return new ResponseEntity(new ErrorMessage("23T12", String.format("%s không tồn tại với %s: %s",
				ex.getResourceName(), ex.getFieldName(), ex.getFieldValue()), HttpStatus.NOT_FOUND),
				HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler
	public ResponseEntity<Map<String, String>> handleValidateException(ValidateException ex) {
		return new ResponseEntity(ex.getMessageMap(), ex.getStatus());
	}

}
