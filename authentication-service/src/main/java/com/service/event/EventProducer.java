package com.service.event;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

		log.info("Producer topics Profile");

		return sender.send(Mono.just(SenderRecord.create(new ProducerRecord<>(topics, message), message))).then()
				.thenReturn("OK");
	}
}
