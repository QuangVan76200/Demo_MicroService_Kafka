package com.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.service.dto.MailStructure;

@Service
public class MailService {

	@Autowired
	private  MailSender mailSender;
	
	@Value("${spring.mail.username}")
	private String fromMail;
	
	public void sendMail(String mail, MailStructure mailStructure) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(fromMail);
		
		mailMessage.setSubject(mailStructure.getSubject());
		mailMessage.setText(mailStructure.getMessage());
		mailMessage.setTo(mail);
		
		mailSender.send(mailMessage);
	}
}
