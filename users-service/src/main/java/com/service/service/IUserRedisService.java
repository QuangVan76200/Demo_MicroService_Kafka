/**
 * 
 */
package com.service.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.service.dto.UserDTO;

public interface IUserRedisService {
	
	void clear();

	List<UserDTO> getAllUser(
			String keyword, 
			PageRequest pageRequest) throws JsonProcessingException;
	
	void saveAllUsers(List<UserDTO> userDTOs, 
			String keyword, 
			PageRequest pageRequest) throws JsonProcessingException;
}
