package com.service.dto;

import java.time.LocalDateTime;

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

	private Boolean isActive;

	private LocalDateTime createdDate;

	private LocalDateTime updatedDate;

	private String role;

	public static User mapDtoToEntity(UserDTO userDTO) {
		return User.builder()
				.email(userDTO.getEmail())
				.password(userDTO.getPassword())
				.username(userDTO.getUsername())
				.numberphone(userDTO.getNumberPhone())
				.createddate(userDTO.getCreatedDate())
				.updateddate(userDTO.getUpdatedDate())
				.role(userDTO.getRole())
				.build();
	}
	
	public static UserDTO mapEntityToDto(User userVO) {
		return UserDTO.builder()
				.id(userVO.getId())
				.email(userVO.getEmail())
				.password(userVO.getPassword())
				.username(userVO.getUsername())
				.numberPhone(userVO.getNumberphone())
				.createdDate(userVO.getCreateddate())
				.updatedDate(userVO.getUpdateddate())
				.role(userVO.getRole())
				.build();
	}

}

