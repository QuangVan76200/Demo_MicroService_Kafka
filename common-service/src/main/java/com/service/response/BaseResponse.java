/**
 * 
 */
package com.service.response;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaseResponse<T> implements Serializable {
    private HttpStatus status;
    private String message;
    private T data;
    @JsonFormat(pattern = "dd/MM/yyyy h:mm:ss a")
    private LocalDateTime dateTime;
}
