package com.paulo.springcourseaws.dto;

import javax.validation.constraints.NotNull;

import com.paulo.springcourseaws.domain.Request;
import com.paulo.springcourseaws.domain.RequestStage;
import com.paulo.springcourseaws.domain.User;
import com.paulo.springcourseaws.domain.enums.RequestState;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class RequestStagesSaveDTO {

	@NotNull(message = "State required")
	private String description;
	
	@NotNull(message = "State required")
	private RequestState state;
	
	@NotNull(message = "Request required")
	private Request request;
	
	@NotNull(message = "Owner required")
	private User owner;
	
	public RequestStage transformToRequestStage() {
		RequestStage requestStage = new RequestStage(null, this.description, null, this.state, this.request, this.owner);
		return requestStage;
	}
}
