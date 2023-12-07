package com.service.dto;

import com.service.entities.AuthVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthDTO {

	private String id;
	private String email;
	private String password;
	
	private String fullName;

	private String identificationDocuments;
	
	private String nationality;
	
	private String permanentAdress;
	
	private String dateOfBirth;

	private String numberPhone;
	
	private String userName;

	private Boolean isActive;

	private String role;

	public static AuthVO mapDtoToEntity(AuthDTO dto) {
		return AuthVO.builder()
				.email(dto.getEmail())
				.password(dto.getPassword())
				.userName(dto.getUserName())
				.isActive(dto.getIsActive())
				.role(dto.getRole())
				.build();
	}
	
	public static AuthDTO mapEntityToDto(AuthVO vo) {
		return AuthDTO.builder()
				.email(vo.getEmail())
				.password(vo.getPassword())
				.userName(vo.getUserName())
				.isActive(vo.getIsActive())
				.role(vo.getRole())
				.build();
	}
}
