package com.service.dto;

import java.time.LocalDateTime;
import java.util.Date;

import com.service.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

	private Long id;

	private String email;

	private String password;

	private String username;

	private String numberPhone;
	
	private String fullName;
	
	private String identificationDocuments;
	
	private String nationality;

	private String permanentAdress;
	
	private Date dateOfBirth;

	private Boolean isActive;

	private LocalDateTime createdDate;

	private LocalDateTime updatedDate;

	private String role;

	public static User mapDtoToEntity(UserDTO userDTO) {
		return User.builder()
				.email(userDTO.getEmail())
				.username(userDTO.getUsername())
				.numberphone(userDTO.getNumberPhone())
				.fullName(userDTO.getFullName())
				.identificationDocuments(userDTO.getIdentificationDocuments())
				.nationality(userDTO.getNationality())
				.permanentAdress(userDTO.getPermanentAdress())
				.dateOfBirth(userDTO.getDateOfBirth())
				.createddate(userDTO.getCreatedDate())
				.updateddate(userDTO.getUpdatedDate())
				.isActive(userDTO.getIsActive())
				.role(userDTO.getRole())
				.build();
	}
	
	public static UserDTO mapEntityToDto(User userVO) {
		return UserDTO.builder()
				.id(userVO.getId())
				.email(userVO.getEmail())
				.username(userVO.getUsername())
				.numberPhone(userVO.getNumberphone())
				.fullName(userVO.getFullName())
				.identificationDocuments(userVO.getIdentificationDocuments())
				.nationality(userVO.getNationality())
				.permanentAdress(userVO.getPermanentAdress())
				.dateOfBirth(userVO.getDateOfBirth())
				.createdDate(userVO.getCreateddate())
				.updatedDate(userVO.getUpdateddate())
				.isActive(userVO.getIsActive())
				.role(userVO.getRole())
				.build();
	}

}

