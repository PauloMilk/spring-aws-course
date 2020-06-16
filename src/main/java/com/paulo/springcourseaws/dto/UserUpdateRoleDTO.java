package com.paulo.springcourseaws.dto;

import javax.validation.constraints.NotNull;

import com.paulo.springcourseaws.domain.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class UserUpdateRoleDTO {

	@NotNull(message = "Role required")
	private Role role;
	
}
