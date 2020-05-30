package com.paulo.springcourseaws.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.paulo.springcourseaws.domain.Request;
import com.paulo.springcourseaws.domain.RequestStage;
import com.paulo.springcourseaws.domain.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDTO {
	@NotBlank
	private String name;
	
	@Email
	private String email;
	
	@Size(min = 7, max = 99, message = "Password must be beetween 7 and 99")
	private String password;
		
	private List<Request> requests = new ArrayList<Request>();
	private List<RequestStage> stages = new ArrayList<RequestStage>();
	
	public User transformToUser() {
		User user = new User(null, this.getName() , this.getEmail() , this.getPassword(), null, this.getRequests(), this.getStages());
		return user;
	}
}
