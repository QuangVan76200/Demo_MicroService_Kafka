package com.service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.dto.AuthLoginRequestDTO;
import com.service.dto.AuthRegisterRequestDTO;
import com.service.dto.AuthResponseDTO;
import com.service.dto.MailStructure;
import com.service.services.AuthService;
import com.service.services.MailService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

	private final AuthService authService;
	
	private final MailService mailService;

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody AuthRegisterRequestDTO request) {
		System.out.println("Info saved...");

		return ResponseEntity.ok(authService.register(request));
	}

	@PostMapping("/login")
	public AuthResponseDTO login(@RequestBody AuthLoginRequestDTO dto) {

		return authService.login(dto);
		
	}
	
	@PostMapping("/send/{mail}")
	public String sendMail(@PathVariable String mail, @RequestBody MailStructure mailStructure) {
		mailService.sendMail(mail, mailStructure);
		
		return "Send Email to "+ mail+ " SUCESSFFULL";
	}
	
	

}
