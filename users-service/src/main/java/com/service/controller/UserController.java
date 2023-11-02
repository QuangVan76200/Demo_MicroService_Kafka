package com.service.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.dto.UserDTO;
import com.service.service.UserService;

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
		return Optional.ofNullable(userService.getUserByUsername(username))
				.map(user -> ResponseEntity.ok(user))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

}
