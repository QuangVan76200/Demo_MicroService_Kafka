package com.service.common;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class CommonException extends RuntimeException {

	private String code;

	private String message;

	private HttpStatus status;

	public CommonException(String code, String message, HttpStatus status) {
		super();
		this.code = code;
		this.message = message;
		this.status = status;
	}
	
	

}