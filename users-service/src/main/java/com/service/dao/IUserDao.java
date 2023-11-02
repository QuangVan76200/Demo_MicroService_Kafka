package com.service.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.service.entity.User;

public interface IUserDao extends JpaRepository<User, Long>{
	
	Optional<User> findByEmail(String email);
	
	Optional<User> findByUsername(String userName);
	
	Optional<User> findByUsernameOrEmailOrNumberphone(String username, String email, String numberPhone);
	
	Optional<User> findByNumberphone(String numberPhone);

}
