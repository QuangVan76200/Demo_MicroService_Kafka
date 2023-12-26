package com.service.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.service.entity.User;

public interface IUserDao extends JpaRepository<User, Long>{
	
	Optional<User> findByEmail(String email);
	
	Optional<User> findByUsername(String userName);
	
	Optional<User> findByUsernameOrEmailOrNumberphone(String username, String email, String numberPhone);
	
	Optional<User> findByNumberphone(String numberPhone);
	
	@Query("SELECT  o FROM User o WHERE o.isActive = 1 AND (:keyword IS NULL OR :keyword = '' OR " +
			"o.fullName LIKE %:keyword% " +
			"OR o.permanentAdress LIKE %:keyword% " +
			"OR o.email LIKE %:keyword%) ")
	Page<User> findAll( @Param("keyword") String keyword, Pageable pageable);

}
