package com.service.configuaration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;

@Configuration
public class CommonKafkaConfiguaration {

	private ReactiveKafkaAppProperties reactiveKafkaAppProperties;

	public CommonKafkaConfiguaration(ReactiveKafkaAppProperties reactiveKafkaAppProperties) {
		this.reactiveKafkaAppProperties = reactiveKafkaAppProperties;
	}

	@Bean
	KafkaSender<String, String> kafkaSender() {
		Map<String, Object> props = new HashMap<>();

		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, reactiveKafkaAppProperties.bootstrapServers);
		props.put(ProducerConfig.ACKS_CONFIG, "all");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		SenderOptions<String, String> senderOptions = SenderOptions.create(props);

		return KafkaSender.create(senderOptions);
	}

	@Bean
	ReceiverOptions<String, String> receiverOptions() {
		Map<String, Object> propsReceiver = new HashMap<>();
		propsReceiver.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, reactiveKafkaAppProperties.bootstrapServers);
		propsReceiver.put(ConsumerConfig.GROUP_ID_CONFIG, reactiveKafkaAppProperties.consumerGroupId);
		propsReceiver.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		propsReceiver.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		propsReceiver.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest"); 
		propsReceiver.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 100); 
		propsReceiver.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, 1);
		return ReceiverOptions.create(propsReceiver);
	}

}
