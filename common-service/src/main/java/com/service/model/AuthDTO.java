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

	private String numberPhone;
	private String userName;

	private Boolean isActive;

	private String role;
}
