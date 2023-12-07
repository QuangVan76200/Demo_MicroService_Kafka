package com.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthDTO {

	private String email;
	
	private String password;

	private String userName;
	
	private String fullName;

	private String identificationDocuments;
	
	private String nationality;
	
	private String permanentAdress;
	
	private String dateOfBirth;
	
	private String numberPhone;
	
	private Boolean isActive;

	private String role;
}
