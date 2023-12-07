/**
 * 
 */
package com.service.emailservice.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailStructure {

	private String subject;
	private String message;
	private List<String> cc;
}
