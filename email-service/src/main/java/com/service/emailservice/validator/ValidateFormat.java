/**
 * 
 */
package com.service.emailservice.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class ValidateFormat {

	public static final String FORMAT_EMAIL = "[a-z0-9._%+-]+@gmail.com";

	public boolean isValidEmail(String email) {
		Pattern pattern = Pattern.compile(FORMAT_EMAIL);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

}
