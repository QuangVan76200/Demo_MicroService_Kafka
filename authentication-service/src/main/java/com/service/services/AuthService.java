package com.service.services;

import java.security.SecureRandom;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.service.common.CommonException;
import com.service.common.ResourceNotFoundException;
import com.service.constant.Constants;
import com.service.dao.IAuthDao;
import com.service.dto.AuthDTO;
import com.service.dto.request.AuthLoginRequestDTO;
import com.service.dto.request.AuthRegisterRequestDTO;
import com.service.dto.request.RenewPasswordRequest;
import com.service.dto.response.AuthResponseDTO;
import com.service.entities.AuthVO;
import com.service.event.EventProducer;
import com.service.model.ForgotPasswordDTO;
import com.service.response.BaseResponse;
import com.service.response.ResponseFactory;
import com.service.utils.Constant;
import com.service.validation.ValidateFormat;

import io.micrometer.core.ipc.http.HttpSender.Response;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthService {

	private final RestTemplate restTemplate;
	private final JwtUtil jwtUtil;
	private final ValidateFormat validateFormat;
	private final EventProducer eventProducer;
	private final IAuthDao authDao;
	static SecureRandom random = new SecureRandom();
	private final ResponseFactory responseFactory;

	private Gson gson = new Gson();

	private static final String USER_SERVICE = "http://user-service/users";

	public String register(AuthRegisterRequestDTO request) {

		try {

			validateInput(request);

			String encodedPassword = new BCryptPasswordEncoder().encode(request.getPassword());

			request.setPassword(encodedPassword);

			AuthDTO user = requestToObject(request);

			authAccount(user);
			
			String key = "user:" + user.getEmail(); 
//			redisTemplateString.opsForValue().set(key, user);

			eventProducer.send(Constant.ACCOUNT_ONBOARDING_TOPIC, gson.toJson(user)).subscribe();

			return Constants.REGISTER_SUCCESSFULL;
		} catch (Exception e) {
			throw new CommonException("PDF01", Constants.REGISTER_FAILED + " " + e.getMessage(),
					HttpStatus.BAD_REQUEST);
		}

	}

	public AuthDTO authAccount(AuthDTO authDTO) {

		// Check if email exists
		Optional<AuthVO> checkEmail = authDao.findByEmail(authDTO.getEmail());
		if (checkEmail.isPresent()) {
			throw new CommonException("PD01 Error", "Email Already Exists", HttpStatus.BAD_REQUEST);
		}

		AuthVO authVO = AuthDTO.mapDtoToEntity(authDTO);
		authVO.setId(UUID.randomUUID().toString());
		authVO.setIsActive(Boolean.FALSE);
		authDao.save(authVO);
		return AuthDTO.mapEntityToDto(authVO);
	}

	@SneakyThrows
	public AuthDTO updateStatusAccount(AuthDTO authDTO) {
		Optional<AuthVO> authVOOptional = authDao.findByEmail(authDTO.getEmail());

		eventProducer.send(Constant.SEND_MAIL_SUBJECT_CLIENT_REGISTER, gson.toJson(authDTO))
			.subscribe();

		return authVOOptional.map(authVO -> {
			authVO.setIsActive(Boolean.TRUE);
			authDao.save(authVO);
			return AuthDTO.mapEntityToDto(authVO);
		}).orElse(null);

	}

	@Transactional
	public void rollbackProfile(String email) {
		log.info("ROLLBACK_DATA");
		Optional<AuthVO> authVOOptional = authDao.findByEmail(email);
		authVOOptional.ifPresent(authVO -> {
			authDao.deleteByEmail(email);
		});
	}

	public AuthResponseDTO login(AuthLoginRequestDTO request) {
		try {
			AuthDTO currentUser = getUserByUsername(request.getUserName());

			if (Objects.isNull(currentUser)) {
				throw new CommonException("PDF01", Constants.LOGGIN_FAILED + " User not found", HttpStatus.BAD_REQUEST);
			}

			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			if (!passwordEncoder.matches(request.getPassword(), currentUser.getPassword())) {
				throw new CommonException("PDF01", Constants.LOGGIN_FAILED + " Incorrect Password",
						HttpStatus.BAD_REQUEST);
			}

			String accessToken = jwtUtil.generate(currentUser.getId(), currentUser.getRole(), "ACCESS");
			String refreshToken = jwtUtil.generate(currentUser.getId(), currentUser.getRole(), "REFRESH");

			AuthResponseDTO dto = new AuthResponseDTO();
			dto.setAccessToken(accessToken);
			dto.setRefreshToken(refreshToken);

			return dto;
		} catch (CommonException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CommonException("PDF01", Constants.LOGGIN_FAILED + " " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	private AuthDTO getUserByUsername(String username) {
		String url = USER_SERVICE + "/{userName}";
		return restTemplate.getForObject(url, AuthDTO.class, username);
	}

	private void validateInput(AuthRegisterRequestDTO request) {
//		if (!validateFormat.isValidEmail(request.getEmail())) {
//			throw new CommonException("PD04 Error", "Invalid email format", HttpStatus.BAD_REQUEST);
//		}

		if (!validateFormat.isValidNumberPhone(request.getNumberPhone())) {
			throw new CommonException("PD04 Error", "Invalid numberphone format", HttpStatus.BAD_REQUEST);
		}

		if (!validateFormat.isValidPassword(request.getPassword())) {
			throw new CommonException("PD04 Error", "Invalid password format", HttpStatus.BAD_REQUEST);
		}

		if (!validateFormat.rolesContains(request.getRole())) {
			throw new CommonException("PD04 Error", "Invalid role", HttpStatus.BAD_REQUEST);
		}
	}

	public static AuthDTO requestToObject(AuthRegisterRequestDTO dto) {
		AuthDTO user = new AuthDTO();
		user.setId(dto.getId());
		user.setEmail(dto.getEmail());
		user.setUserName(dto.getUserName());
		user.setNumberPhone(dto.getNumberPhone());
		user.setFullName(dto.getFullName());
		user.setIdentificationDocuments(dto.getIdentificationDocuments());
		user.setNationality(dto.getNationality());
		user.setPermanentAdress(dto.getPermanentAdress());
		user.setDateOfBirth(dto.getDateOfBirth());

		user.setIsActive(Boolean.FALSE);
		user.setPassword(dto.getPassword());

		user.setRole(dto.getRole());

		return user;
	}
	
	public ResponseEntity<BaseResponse<String>> forgotPasswordRequest(String email) {
		AuthVO authUser = authDao.findByEmail(email).orElseThrow(() 
				-> new ResourceNotFoundException("User", "email", email));

		String activeCode = String.valueOf(new Random().nextInt(900000) + 100000);
		ForgotPasswordDTO forgotPassword = new ForgotPasswordDTO(activeCode , email);
		authUser.setPinCode(activeCode);
		authDao.save(authUser);
		eventProducer.send(Constant.PASSWORD_FORGOT, gson.toJson(forgotPassword)).subscribe();
		log.info("Mã xác nhận đã được gửi tới email " + email);
		
		return responseFactory.success("Mã xác nhận đã được gửi tới email ", email);
	}
	
	public ResponseEntity<BaseResponse<Boolean>> checkPinCode(String email, String pinCode) {
		if(pinCode.isEmpty()) {
			return responseFactory.fail(HttpStatus.BAD_REQUEST, "Pin Code is not be Empty", Boolean.FALSE);
		}
		String value  = authDao.findByPinCode(email);
		if(!value.equals(pinCode)) {
			return responseFactory.fail(HttpStatus.BAD_REQUEST, "Pin Code Incorrect", Boolean.FALSE);
		}
		return responseFactory.success("Pin Code IsCorrect", Boolean.TRUE);
	}

	public ResponseEntity<BaseResponse<String>> renewPassword(RenewPasswordRequest request) {
		if(!request.getNewPassword().equals(request.getRetypePassword())) {
			throw new CommonException("PD098", "passwords do not match", HttpStatus.BAD_REQUEST);
		}
		Optional<AuthVO> userOptional = authDao.findByEmail(request.getEmail());
		if(userOptional.isPresent()) {
			AuthVO user = userOptional.get();
			String encodedPassword = new BCryptPasswordEncoder().encode(request.getNewPassword());
			user.setPassword(encodedPassword);
			authDao.save(user);
			return responseFactory.success("Password setup successful, please log in again", request.getNewPassword());
		}
		throw new CommonException("PD023", "Mã xác thực không chính xác hoặc đã hết hạn", HttpStatus.UNAUTHORIZED);
	}
	
}
