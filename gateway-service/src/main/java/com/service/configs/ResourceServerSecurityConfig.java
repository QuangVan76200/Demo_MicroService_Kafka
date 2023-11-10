package com.service.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;


@Configuration
public class ResourceServerSecurityConfig {

	@Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity) {
        return serverHttpSecurity
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange(authorizeExchange ->
                authorizeExchange
                    .pathMatchers("/eureka/**", "/auth/login", "/auth/register")
                    .permitAll()
//                    .pathMatchers("/api/employees").hasRole("EMPLOYEE")
//                    .pathMatchers("/api/employees/**").hasRole("EMPLOYEE")
//                    .pathMatchers(HttpMethod.POST, "/api/employees").hasRole("MANAGER")
//                    .pathMatchers(HttpMethod.PUT, "/api/employees").hasRole("MANAGER")
//                    .pathMatchers(HttpMethod.DELETE, "/api/employees").hasRole("ADMIN")
                    .anyExchange()
                    .authenticated()
            )
            .oauth2ResourceServer(oauth -> oauth
                .jwt(Customizer.withDefaults()))
            .build();
    }
	

}
