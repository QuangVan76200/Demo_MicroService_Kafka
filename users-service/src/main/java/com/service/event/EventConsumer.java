package com.service.event;

import java.time.LocalDateTime;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.service.dto.UserDTO;
import com.service.model.AuthDTO;
import com.service.service.UserService;
import com.service.utils.Constant;
import com.service.utils.LocalDateTimeAdapter;

import lombok.extern.slf4j.Slf4j;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;

@Service
@Slf4j
public class EventConsumer {

	Gson gson = new GsonBuilder()
	        .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
	        .create();
	
	@Autowired
	private UserService userService;
	@Autowired
	private EventProducer eventProducer;

	public EventConsumer(ReceiverOptions<String, String> receiverOptions) {
		KafkaReceiver.create(receiverOptions.subscription(Collections.singleton(Constant.ACCOUNT_ONBOARDING_TOPIC)))
				.receive().subscribe(this::userOnboarding);
	}

	public void userOnboarding(ReceiverRecord<String, String> receiverRecord) {
		log.info("User Infor Onboarding event " + receiverRecord.value());
		AuthDTO user = gson.fromJson(receiverRecord.value(), AuthDTO.class);
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername(user.getUserName());
		userDTO.setEmail(user.getEmail());
		userDTO.setNumberPhone(user.getNumberPhone());
		userDTO.setPassword(user.getPassword());
		userDTO.setRole(user.getRole());

		try {
			userService.signUp(userDTO);

			log.info("New Account Success");
			log.info("Send successful account creation event to complete the Saga process");
			eventProducer.send(Constant.ACCOUNT_ONBOARDED_TOPIC, gson.toJson(user)).subscribe();
		} catch (Exception e) {
			log.info("New Account Failed");
			log.info("Handle error when creating new Account and perform revert by sending revert event");
			log.error(e.getMessage());
			eventProducer.send(Constant.ACCOUNT_CREATION_FAILED_TOPIC, gson.toJson(userDTO)).subscribe();
		}
	}

}
