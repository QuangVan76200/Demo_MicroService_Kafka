package com.service.entity;

import java.time.LocalDateTime;
import java.util.Date;

import com.service.listener.UserListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "User")
@Builder
//@EntityListeners(UserListener.class)
public class User {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	private String email;
	
	private String username;
	
	private String numberphone;
	
	private String fullName;
	
	private String identificationDocuments;
	
	private String nationality;

//	@Column(name="permanent_adress")
	private String permanentAdress;
	
	private Date dateOfBirth;
	
	@Column(name="is_active")
	private Boolean isActive;
	
	private String accountNumber;
	
	private String pinCode;
	
	private LocalDateTime createddate;
	private LocalDateTime updateddate;

	private String role;
}
