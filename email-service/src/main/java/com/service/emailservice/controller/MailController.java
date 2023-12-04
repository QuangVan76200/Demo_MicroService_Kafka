/**
 * 
 */
package com.service.emailservice.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.emailservice.dto.MailStructure;
import com.service.emailservice.service.MailService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {

	
	private final MailService mailService;
	
	@PostMapping("/send/{mail}")
	public String sendMail(@PathVariable String mail, @RequestBody MailStructure mailStructure) {
		mailService.sendMail(mail, mailStructure);
		
		return "Send Email to "+ mail+ " SUCESSFFULL";
	}
}
