package com.service.payload.response;

import com.service.dto.UserDTO;
import com.service.response.PageResponse;

import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
public class PageResponseUsers extends PageResponse<UserDTO> {

}
