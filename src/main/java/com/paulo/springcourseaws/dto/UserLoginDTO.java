package com.paulo.springcourseaws.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class UserLoginDTO {

	@Email(message = "Email is required")
	private String email;
	
	@NotBlank(message = "Password is required")
	private String password;

}
