package com.service.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.service.enums.Roles;

@Component
public class ValidateFormat {

	
	public static final String FORMAT_EMAIL = "[a-z0-9._%+-]+@fsoft.com";
	
	public static final String NUMBER_PHONE = "^(0[3|5|7|8|9][0-9]{8})$";
	
	public static final String PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";
	
	public boolean isValidEmail(String email) {
	    Pattern pattern = Pattern.compile(FORMAT_EMAIL);
	    Matcher matcher = pattern.matcher(email);
	    return matcher.matches();
	}
	
	public boolean isValidNumberPhone(String numberPhone) {
	    Pattern pattern = Pattern.compile(NUMBER_PHONE);
	    Matcher matcher = pattern.matcher(numberPhone);
	    return matcher.matches();
	}
	
	public boolean isValidPassword(String password) {
	    Pattern pattern = Pattern.compile(PASSWORD);
	    Matcher matcher = pattern.matcher(password);
	    return matcher.matches();
	}
	
	public boolean rolesContains(String role) {
	    try {
	        Roles.valueOf(role); 
	        return true;
	    } catch (IllegalArgumentException ex) {
	        return false; 
	    }
	}
}
