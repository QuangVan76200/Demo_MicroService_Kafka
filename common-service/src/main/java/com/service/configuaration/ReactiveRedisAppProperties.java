/**
 * 
 */
package com.service.configuaration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReactiveRedisAppProperties {
	
	@Value("${spring.data.redis.host}")
	String redisHost;

	@Value("${spring.data.redis.port}")
	int redisPort;

}
