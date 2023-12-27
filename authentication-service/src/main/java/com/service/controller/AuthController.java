package com.service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.service.dto.request.AuthLoginRequestDTO;
import com.service.dto.request.AuthRegisterRequestDTO;
import com.service.dto.request.RenewPasswordRequest;
import com.service.dto.response.AuthResponseDTO;
import com.service.response.BaseResponse;
import com.service.services.AuthService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody AuthRegisterRequestDTO request) {
		System.out.println("Info saved...");

		return ResponseEntity.ok(authService.register(request));
	}

	@PostMapping("/login")
	public AuthResponseDTO login(@RequestBody AuthLoginRequestDTO dto) {

		return authService.login(dto);

	}

	@PostMapping("/password/forgot/{email}")
	public ResponseEntity<BaseResponse<String>> forgotPasswordRequest(@PathVariable String email) {
		return authService.forgotPasswordRequest(email);
	}
	
	@PostMapping("/password/renew")
    public ResponseEntity<BaseResponse<String>> renewPassword(@RequestBody RenewPasswordRequest request) {
        return authService.renewPassword(request);
    }
	
	@GetMapping("/password/pinCode")
    public ResponseEntity<BaseResponse<Boolean>> checkPinCode(
    		@RequestParam(value = "email", defaultValue = "", required = true) String email,
    		@RequestParam(value = "pinCode", defaultValue = "", required = true) String pinCode) {
        return authService.checkPinCode(email, pinCode);
    }

}
