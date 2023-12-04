package com.service.event;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.service.dto.AuthDTO;
import com.service.services.AuthService;
import com.service.utils.Constant;

import lombok.extern.slf4j.Slf4j;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;

@Service
@Slf4j
public class EventConsumer {

	Gson gson = new Gson();

	@Autowired
	private AuthService authService;

	public EventConsumer(ReceiverOptions<String, String> receiverOptions) {
		KafkaReceiver.create(receiverOptions.subscription(Collections.singleton(Constant.ACCOUNT_ONBOARDED_TOPIC)))
				.receive().subscribe(this::accountOnboarded);

		KafkaReceiver
				.create(receiverOptions.subscription(Collections.singleton(Constant.ACCOUNT_CREATION_FAILED_TOPIC)))
				.receive().subscribe(this::rollbackAccount);
	}

	public void accountOnboarded(ReceiverRecord<String, String> receiverRecord) {
		log.info("ACCOUNT_ONBOARDED_TOPIC");
		AuthDTO user = gson.fromJson(receiverRecord.value(), AuthDTO.class);
		authService.updateStatusAccount(user);
		log.info("----------------ACCOUNT BE CREATED--------------");

	}

	public void rollbackAccount(ReceiverRecord<String, String> receiverRecord) {
		log.info("PROFILE_CREATION_FAILED_TOPIC");
		AuthDTO user = gson.fromJson(receiverRecord.value(), AuthDTO.class);
		authService.rollbackProfile(user.getEmail());
		log.info("----------------ACCOUNT FAILED TO CREAT--------------");
	}

}
