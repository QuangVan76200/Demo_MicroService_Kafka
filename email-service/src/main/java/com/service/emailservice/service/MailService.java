package com.service.emailservice.service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.mail.MailSender;
//import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.service.emailservice.dto.MailStructure;
import com.service.emailservice.validator.ValidateFormat;
import com.service.model.AuthDTO;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MailService {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private ValidateFormat format;

	@Autowired
	private IThymleafService thymleafService;

	@Value("${spring.mail.username}")
	private String fromMail;
	
	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy h:mm:ss a");

//	@PostConstruct
//	public void init() {
//		log.info("EventConsumer initialized");
//		initData();
//	}

	public void sendMailMessage(String mail, MailStructure mailStructure) {

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(fromMail);

		mailMessage.setSubject(mailStructure.getSubject());
		mailMessage.setText(mailStructure.getMessage());
		mailMessage.setTo(mail);

		mailSender.send(mailMessage);

	}
	
	public void sendMailCodeForgotPassword(String mail, MailStructure mailStructure) {

		try {
			MimeMessage mailMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mailMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			Map<String, Object> variables = new HashMap<>();
			variables.put("code", mailStructure.getMessage());
			
			helper.setText(mailStructure.getMessage());
			String htmlContent = thymleafService
					.createContext("forgot-password-template.html", variables);
	        helper.setText(htmlContent, true);
			helper.setFrom(fromMail);
			helper.setSubject(mailStructure.getSubject());
			helper.setTo(mail);
			mailSender.send(mailMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void sendMailAnnouncement(String mail, MailStructure mailStructure, AuthDTO authDTO) {

		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());

			if (mailStructure.getCc() != null) {
				Object[] bccObject = mailStructure.getCc().toArray();
				String[] bcc = new String[bccObject.length];
				for (int i = 0; i < bccObject.length; i++) {
					bcc[i] = String.valueOf(bccObject[i]);
				}
				helper.setBcc(bcc);
			}
			Map<String, Object> variables = new HashMap<>();
			LocalDateTime now = LocalDateTime.now();
			variables.put("transaction_date", now.format(formatter));
			variables.put("full_name", authDTO.getFullName());
			variables.put("date_of_birth", authDTO.getDateOfBirth());
			variables.put("identification_documents", authDTO.getIdentificationDocuments());
			variables.put("nationality", authDTO.getNationality());
			variables.put("performance_adress", authDTO.getPermanentAdress());
			
			helper.setText(mailStructure.getMessage());
			String htmlContent = thymleafService
					.createContext("create-customer-mail-template.html", variables);
	        helper.setText(htmlContent, true);
			helper.setFrom(fromMail);
			helper.setSubject(mailStructure.getSubject());
			helper.setTo(mail);
			mailSender.send(message);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

//	private void initData() {
//		User user = new User();
//		LocalDateTime now = LocalDateTime.now();
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy h:mm:ss a");
//		user.setTransactionDate(now.format(formatter));
//		user.setFullName("Le Quang Van");
//		user.setNationality("VIET NAM");
//		user.setPermanentAdress("DA NANG");
//		user.setDateOfBirth("09/09/2000");
//		this.listUsers = new ArrayList<>();
//		listUsers.add(user);
//
//	}

}
