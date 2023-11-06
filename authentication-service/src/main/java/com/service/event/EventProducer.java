package com.service.event;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.service.common.CommonException;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

@Service
@Slf4j
public class EventProducer {

	@Autowired
	private KafkaSender<String, String> sender;

	public Mono<String> send(String topics, String message) {
		
		try {
			log.info("Producer topics");

			return sender.send(Mono.just(SenderRecord.create(new ProducerRecord<>(topics, message), message))).then()
					.thenReturn("OK");
		} catch (Exception e) {
			 log.error("Failed to send message to topic: " + topics, e);
			 throw new CommonException("PD01", "Failed to send message", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
