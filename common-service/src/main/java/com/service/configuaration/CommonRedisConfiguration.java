package com.service.configuaration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class CommonRedisConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(CommonRedisConfiguration.class);

	private final ReactiveRedisAppProperties reactiveRedisAppProperties;

	@Bean
	public LettuceConnectionFactory redisConnectionFactory() {
		logger.info("Inside redisConnectionFactory()");
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(
				reactiveRedisAppProperties.redisHost, reactiveRedisAppProperties.redisPort);
		logger.info("Connection to RedisConection Factory");
		return new LettuceConnectionFactory(configuration);
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		logger.info("redisHost  " + reactiveRedisAppProperties.redisHost);
		logger.info("redisPort  " + reactiveRedisAppProperties.redisPort);
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory());

		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));

		template.setHashKeySerializer(new StringRedisSerializer());
		template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));

		template.afterPropertiesSet();

		return template;
	}

	@Bean
	public ObjectMapper redisObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();

		module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME));
		module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME));
		objectMapper.registerModule(module);

		return objectMapper;
	}

}
