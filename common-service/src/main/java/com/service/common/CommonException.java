package com.service.common;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class CommonException extends RuntimeException{
    private final String code;
    private final String message;
    private final HttpStatus status;
    public CommonException(String code,String message,HttpStatus status) {
        super(message);
        this.code = code;
        this.message = message;
        this.status = status;
    }
}