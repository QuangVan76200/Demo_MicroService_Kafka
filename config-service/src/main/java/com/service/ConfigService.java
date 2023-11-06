package com.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableEurekaClient
@EnableConfigServer
@EnableAsync
public class ConfigService {

	
	public static void main(String[] args) {
		SpringApplication.run( ConfigService.class);
	}
}
