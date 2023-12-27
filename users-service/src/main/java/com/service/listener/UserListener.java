/**
 * 
 */
package com.service.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.service.entity.User;
import com.service.service.IUserRedisService;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserListener {

	private final IUserRedisService userRedisService;
	
	private static final Logger logger = LoggerFactory.getLogger(UserListener.class);
	
	@PrePersist
	public void prePersit(User user) {
		logger.info("prePersit");
		userRedisService.clear();
	}
	
	
	@PostPersist
	public void postPersits(User user) {
		logger.info("postPersit");
		
		
	}
	
	@PreUpdate
	public void preUpdate(User  user) {
		logger.info("prePersit");
		
		
	}
	
	@PostUpdate
	public void postUpdate(User user) {
		logger.info("postPersit");
		userRedisService.clear();
	}
	
}
