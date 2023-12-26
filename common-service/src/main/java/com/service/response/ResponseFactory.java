/**
 * 
 */
package com.service.response;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseFactory {

	public <T> ResponseEntity<BaseResponse<T>> success(String message, T data) {
		BaseResponse response = BaseResponse.<T>builder()
				.status(HttpStatus.OK)
				.message(message)
				.data(data)
				.dateTime(LocalDateTime.now())
				.build();
		
		return ResponseEntity.ok(response);
	}
	
	public <T> ResponseEntity<BaseResponse<T>> fail(HttpStatus status, String message, T data) {
		BaseResponse response = BaseResponse.<T>builder()
				.status(status)
				.message(message)
				.data(data)
				.dateTime(LocalDateTime.now())
				.build();
		return ResponseEntity.status(status).body(response);
	}
}
