package com.paulo.springcourseaws.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.paulo.springcourseaws.domain.Request;
import com.paulo.springcourseaws.domain.RequestStage;
import com.paulo.springcourseaws.domain.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RequestSaveDTO {

	@NotBlank(message = "Subject required")
	private String subject;

	private String description;

	@NotNull(message = "Owner required")
	private User owner;

	private List<RequestStage> stages = new ArrayList<RequestStage>();

	public Request transformToRequest() {
		Request request = new Request(null, this.getSubject(), this.getDescription(), null, null, this.getOwner(),
				this.getStages());
		return request;
	}
}
