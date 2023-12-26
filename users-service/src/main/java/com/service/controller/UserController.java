package com.service.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.service.dto.UserDTO;
import com.service.payload.response.PageResponseUsers;
import com.service.response.BaseResponse;
import com.service.service.UserService;
import com.service.utils.PageConstants;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/users")
@AllArgsConstructor
public class UserController {

	private final UserService userService;

	@GetMapping("/secured")
	public ResponseEntity<String> securedEndpoint() {
		return ResponseEntity.ok("Hello, from secured endpoint!");
	}

	@GetMapping("/{userName}")
	public ResponseEntity<UserDTO> getUserByUsername(@PathVariable(name = "userName") String username) {
		return Optional.ofNullable(userService.getUserByUsername(username)).map(user -> ResponseEntity.ok(user))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/profile/profile-list")
	public ResponseEntity<BaseResponse<PageResponseUsers>> getAllProfile(
			@RequestParam(value = "keyword", defaultValue = "", required = false) String keyword,
			@RequestParam(value = "pageNo", defaultValue = PageConstants.PAGE_NO, required = false) Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = PageConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = PageConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = PageConstants.SORT_DIR, required = false) String sortDir) throws JsonProcessingException {

		
		return userService.getAll(keyword, pageNo, pageSize, sortBy, sortDir);
	}

}
