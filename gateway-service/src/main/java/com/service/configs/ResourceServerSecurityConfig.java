package com.service.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.config.Customizer;


@Configuration
public class ResourceServerSecurityConfig {

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity) {
	    return serverHttpSecurity.csrf(ServerHttpSecurity.CsrfSpec::disable)
	        .authorizeExchange(exchange -> exchange
	            .pathMatchers("/eureka/**", "/auth/login", "/auth/register")
	                .permitAll()
	            .anyExchange()
	                .authenticated()
	        )
	        .oauth2ResourceServer((oauth) -> oauth
	            .jwt(Customizer.withDefaults()))
	        .build();
	}	
	

}
