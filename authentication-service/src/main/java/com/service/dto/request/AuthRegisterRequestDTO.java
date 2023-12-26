package com.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthRegisterRequestDTO {

	private String id;

	private String email;

	private String password;

	private String userName;
	
	private String fullName;

	private String identificationDocuments;
	
	private String nationality;
	
	private String permanentAdress;
	
	private String dateOfBirth;
	
	private String numberPhone;

	private String role;

}
