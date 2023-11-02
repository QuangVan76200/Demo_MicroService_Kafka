package com.service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Account")
@Builder
@Entity
public class AuthVO {

	@Id
	private String id;
	
	@Column(unique = true)
	private String email;
	private String password;
	private String userName;

	private Boolean isActive;

	private String role;

}
