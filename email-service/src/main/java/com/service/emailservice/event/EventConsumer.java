/**
 * 
 */
package com.service.emailservice.event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.service.emailservice.constant.Constants;
import com.service.emailservice.dto.MailStructure;
import com.service.emailservice.service.MailService;
import com.service.model.AuthDTO;
import com.service.model.ForgotPasswordDTO;
import com.service.utils.Constant;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;

@Service
@Slf4j
public class EventConsumer {

	Gson gson = new Gson();
	
	@Autowired
	private MailService mailService;

	public EventConsumer(ReceiverOptions<String, String> receiverOptions) {
		KafkaReceiver.create(receiverOptions.subscription(Collections.singleton(Constant.PASSWORD_FORGOT))).receive()
				.subscribe(this::sendEmailForgotPassword);

		KafkaReceiver
				.create(receiverOptions.subscription(Collections.singleton(Constant.SEND_MAIL_SUBJECT_CLIENT_REGISTER)))
				.receive().subscribe(this::sendMailNotification);
	}

	public void sendMailNotification(ReceiverRecord<String, String> receiverRecord) {
		log.info("SEND_MAIL_SUBJECT_CLIENT_REGISTER");
		AuthDTO authDTO = gson.fromJson(receiverRecord.value(), AuthDTO.class);
		StringBuilder NOTIFICATION = new StringBuilder();
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy h:mm:ss a");

		String formattedDateTime = now.format(formatter);
		NOTIFICATION.append("Quý khách đã đăng ký dịch vụ VCB Digibank thành công vào lúc : ");
		NOTIFICATION.append(formattedDateTime);
		MailStructure mailStructure = new MailStructure(Constants.MAIL_SUBJECT_CLIENT_REGISTER, NOTIFICATION.toString(),
				null);

		mailService.sendMailAnnouncement(authDTO.getEmail(), mailStructure, authDTO);

	}

	public void sendEmailForgotPassword(ReceiverRecord<String, String> receiverRecord) {
		log.info("PASSWORD_FORGOT");
		System.out.println(receiverRecord.value());
		ForgotPasswordDTO forgotPassword = gson.fromJson(receiverRecord.value(), ForgotPasswordDTO.class);
		MailStructure mailStructure = new MailStructure(Constants.FORGOT_MAIL_REQUEST,
				"Your confirm code to forgot password: " + forgotPassword.getCode(), null);
		mailService.sendMailCodeForgotPassword(forgotPassword.getEmail().toString(), mailStructure);
	}

}
