package com.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthRegisterRequestDTO {

	private String id;

	private String email;

	private String password;

	private String userName;

	private String numberPhone;

	private String role;

}
