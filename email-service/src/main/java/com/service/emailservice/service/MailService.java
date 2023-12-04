package com.service.emailservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.MailSender;
//import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.service.common.CommonException;
import com.service.emailservice.dto.MailStructure;
import com.service.emailservice.validator.ValidateFormat;


@Service
public class MailService {

	@Autowired
	private  MailSender mailSender;
	
	@Autowired
	private  ValidateFormat format;
	
	@Value("${spring.mail.username}")
	private String fromMail;
	
	public void sendMail(String mail, MailStructure mailStructure) {
		
		if(!format.isValidEmail(mail)) {
			throw new CommonException("PD05", "Email Incorrect Format", HttpStatus.BAD_REQUEST);
		}
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(fromMail);
		
		mailMessage.setSubject(mailStructure.getSubject());
		mailMessage.setText(mailStructure.getMessage());
		mailMessage.setTo(mail);
		
		mailSender.send(mailMessage);
		
		
	}
}

