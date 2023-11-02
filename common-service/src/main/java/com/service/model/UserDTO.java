package com.service.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	

	private Long id;

	private String email;

	private String password;

	private String username;

	private String numberPhone;

	private Boolean isActive;

	private LocalDateTime createdDate;

	private LocalDateTime updatedDate;

	private String role;

}
