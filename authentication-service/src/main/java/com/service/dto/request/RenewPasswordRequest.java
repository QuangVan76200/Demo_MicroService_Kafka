/**
 * 
 */
package com.service.dto.request;

import org.hibernate.validator.constraints.Email;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RenewPasswordRequest {

	@NotBlank(message = "Mật khẩu cũ không được để trống")
	@NotNull
	@NotEmpty(message = "Mật khẩu cũ không được để trống")
	private String oldPassword;

	@NotBlank(message = "Mật khẩu mới không được để trống")
	@NotNull
	@NotEmpty(message = "Mật khẩu mới không được để trống")
	@Size(min = 8, max = 20, message = "Mật khẩu phải chứa từ 8 đến 20 ký tự")
	private String newPassword;
	
	@Email(regexp = "[a-z0-9._%+-]+@gmail.com", message = "Email không hợp lệ. Phải là địa chỉ email gmail.")
    @NotBlank(message = "Email không được để trống")
    @NotNull
    @NotEmpty(message = "Email không được để trống")
    private String email;

	private String retypePassword;
}
