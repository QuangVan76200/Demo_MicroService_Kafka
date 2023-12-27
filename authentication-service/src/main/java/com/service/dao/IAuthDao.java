package com.service.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.service.entities.AuthVO;


public interface IAuthDao extends JpaRepository<AuthVO, String>{
	
	public Optional<AuthVO> findById(String id);

	@Query(value = "select * FROM account WHERE email = :email", nativeQuery = true)
	public Optional<AuthVO> findByEmail(String email);
	
//	@Query(value = "delete FROM account WHERE email = :email")
	public void deleteByEmail(String email);

	@Query(value = "Select A.pin_code FROM account A where email = :email", nativeQuery = true)
	public String findByPinCode(@Param("email")String email);
}
