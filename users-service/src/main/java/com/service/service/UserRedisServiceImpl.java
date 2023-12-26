/**
 * 
 */
package com.service.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.dto.UserDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserRedisServiceImpl implements IUserRedisService {

	private final RedisTemplate<String, Object> redisTemplate;
	private final ObjectMapper redisOpObjectMapper;
	
	private String getKey(String keyword, 
			PageRequest pageRequest) {
		int pageNumber = pageRequest.getPageNumber();
		int pageSize = pageRequest.getPageSize();
		Sort sort = pageRequest.getSort();
		
		String sortDirection = sort.getOrderFor("id")
				.getDirection() == Sort.Direction.ASC? "asc":"desc";
		
		String key = String.format("all_users:%d:%d:%s", pageNumber, pageSize, sortDirection);
		
		return key;
	}

	@Override
	public List<UserDTO> getAllUser(String keyword, PageRequest pageRequest) 
			throws JsonProcessingException {
		
		String key  = this.getKey(keyword, pageRequest);
		String json = (String) redisTemplate.opsForValue().get(key);
		
		List<UserDTO> usersResponse = 
				json != null ? 
						redisOpObjectMapper.
						readValue(json, new TypeReference<List<UserDTO>>() {}) : null;
		
		return usersResponse;
	}

	@Override
	public void saveAllUsers(List<UserDTO> userDTOs, String keyword, PageRequest pageRequest)
			throws JsonProcessingException {
		String key  = this.getKey(keyword, pageRequest);
		String json = redisOpObjectMapper.writeValueAsString(userDTOs);
		
		redisTemplate.opsForValue().set(key, json);
	}
	
	@Override
	public void clear() {
		redisTemplate.getConnectionFactory().getConnection().flushAll();

	}


}
