/**
 * 
 */
package com.service.dto.request;

import org.springframework.messaging.Message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordDTO {
	
	private String code;
	
	private String email;
	
	
}
