package com.service.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.service.common.CommonException;
import com.service.dao.IUserDao;
import com.service.dto.UserDTO;
import com.service.entity.User;
import com.service.utils.DateUtils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

	private final IUserDao userDao;

	private final DateUtils dateUtils;

	@Async
	public UserDTO signUp(UserDTO userDTO) {

		log.info("validate input");
		validate(userDTO);

		// Check if username exists
		Optional<User> checkUserName = userDao.findByUsername(userDTO.getUsername());
		if (checkUserName.isPresent()) {
			throw new CommonException("PD01 Error", "Username Already Exists", HttpStatus.BAD_REQUEST);
		}

		// Check if number phone exists
		Optional<User> checkNumberPhone = userDao.findByNumberphone(userDTO.getNumberPhone());
		if (checkNumberPhone.isPresent()) {
			throw new CommonException("PD03 Error", "Number Phone Already Exists", HttpStatus.BAD_REQUEST);
		}

		userDTO.setCreatedDate(dateUtils.convertDateToLocalDateTime(new Date()));

		userDTO.setIsActive(Boolean.TRUE);

		User user = UserDTO.mapDtoToEntity(userDTO);

		userDao.save(user);

		UserDTO userDto = UserDTO.mapEntityToDto(user);

		return userDto;

	}

	private void validate(UserDTO userVO) {
		if (userVO == null || StringUtils.isEmpty(userVO.getUsername()) || StringUtils.isEmpty(userVO.getEmail())
				|| StringUtils.isEmpty(userVO.getNumberPhone()) || StringUtils.isEmpty(userVO.getPassword())
				|| StringUtils.isEmpty(userVO.getRole())) {
			throw new CommonException("PD00 Error", "Invalid Input", HttpStatus.BAD_REQUEST);
		}
	}

	public UserDTO getUserByUsername(String username) {
		Optional<User> user = userDao.findByUsername(username);
		if (user.isEmpty()) {
			return null;
		}
		UserDTO userDTO = UserDTO.mapEntityToDto(user.get());
		return userDTO;
	}

}
