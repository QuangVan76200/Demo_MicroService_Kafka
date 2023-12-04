/**
 * 
 */
package com.service.common;

import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.Data;

/**
 * 
 */
@Data
public class ValidateException extends RuntimeException{
    private final String  code;
    private final Map<String,String> messageMap;
    private final HttpStatus status;
    public ValidateException(String code,Map<String,String> message,HttpStatus status){
        this.code = code;
        this.messageMap = message;
        this.status = status;
    }
}