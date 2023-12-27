package com.service.service;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.service.common.CommonException;
import com.service.dao.IUserDao;
import com.service.dto.UserDTO;
import com.service.entity.User;
import com.service.payload.response.PageResponseUsers;
import com.service.response.BaseResponse;
import com.service.response.ResponseFactory;
import com.service.utils.DateUtils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

	private final IUserDao userDao;
	private final DateUtils dateUtils;
	static SecureRandom random = new SecureRandom();
	private final ResponseFactory responseFactory;
	private final IUserRedisService userRedisService;

	public UserDTO signUp(UserDTO userDTO) {

		log.info("validate input");
		validate(userDTO);

		checkIfExists(userDao.findByUsername(userDTO.getUsername()),
				"PD01 Error", "Username Already Exists");
		checkIfExists(userDao.findByNumberphone(userDTO.getNumberPhone()),
				"PD03 Error", "Number Phone Already Exists");
		userDTO.setCreatedDate(dateUtils.convertDateToLocalDateTime(new Date()));

		userDTO.setIsActive(Boolean.TRUE);
		User user = UserDTO.mapDtoToEntity(userDTO);

		user.setAccountNumber("9" + userDTO.getNumberPhone());

		user.setPinCode(randomPinCode());

		userDao.save(user);

		UserDTO userDto = UserDTO.mapEntityToDto(user);

		return userDto;

	}

	public static String randomPinCode() {
		String[] randomPinCode = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" };
		int pinLength = 6;

		String[] generatedPinCode = new String[pinLength];
		for (int i = 0; i < pinLength; i++) {
			int index = random.nextInt(randomPinCode.length);
			generatedPinCode[i] = randomPinCode[index];
		}

		String pinCode = String.join("", generatedPinCode);

		return pinCode;

	}

	private void validate(UserDTO userVO) {
		if (userVO == null || StringUtils.isEmpty(userVO.getUsername()) 
				|| StringUtils.isEmpty(userVO.getEmail())
				|| StringUtils.isEmpty(userVO.getNumberPhone()) 
				|| StringUtils.isEmpty(userVO.getPassword())
				|| StringUtils.isEmpty(userVO.getRole())) {
			throw new CommonException("PD00 Error", "Invalid Input", HttpStatus.BAD_REQUEST);
		}
	}

	private void checkIfExists(Optional<?> optional, String errorCode, String errorMessage) {
		if (optional.isPresent()) {
			throw new CommonException(errorCode, errorMessage, HttpStatus.BAD_REQUEST);
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

	public ResponseEntity<BaseResponse<PageResponseUsers>> getAll(String keyword, Integer pageNo, Integer pageSize,
			String sortBy, String sortDir) throws JsonProcessingException {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();

		PageResponseUsers pageResponse = null;

		PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);
		List<UserDTO> userDTOs = userRedisService.getAllUser(keyword, pageRequest);
		if (userDTOs == null) {
			Page<User> profiles = userDao.findAll(keyword, pageRequest);
			pageResponse = paging(profiles);
			userDTOs = profiles.getContent().stream().map(entity -> UserDTO.mapEntityToDto(entity))
					.collect(Collectors.toList());

			userRedisService.saveAllUsers(userDTOs, keyword, pageRequest);

			return responseFactory.success("Success", pageResponse);
		}

		List<User> userList = userDTOs.stream()
			    .map(user -> UserDTO.mapDtoToEntity(user))
			    .collect(Collectors.toList());

		Page<User> users = new PageImpl<>(userList, pageRequest, userList.size());


		return responseFactory.success("Success", PageResponseUsers.builder()
				.pageNo(users.getNumber())
				.pageSize(userDTOs.size())
				.content(userDTOs)
				.totalPages(users.getTotalPages())
				.totalItems(users.getTotalPages())
				.last(users.isLast())
				.build()
				);
	}

	public PageResponseUsers paging(Page<User> pageUsers) {
		List<UserDTO> userList = pageUsers.getContent().stream().map(entity -> {
			UserDTO user = UserDTO.mapEntityToDto(entity);
			return user;
		}).toList();

		return PageResponseUsers.builder()
				.pageNo(pageUsers.getNumber())
				.pageSize(userList.size())
				.content(userList)
				.totalPages(pageUsers.getTotalPages())
				.totalItems(pageUsers.getTotalPages())
				.last(pageUsers.isLast())
				.build();
	}

}
