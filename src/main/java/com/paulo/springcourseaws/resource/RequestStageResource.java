package com.paulo.springcourseaws.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paulo.springcourseaws.domain.RequestStage;
import com.paulo.springcourseaws.service.RequestStageService;

@RestController
@RequestMapping(value = "request-stages")
public class RequestStageResource {

	@Autowired
	private RequestStageService requestStageService;
	
	@PostMapping
	public ResponseEntity<RequestStage> save(@RequestBody RequestStage requestStage) {
		RequestStage createdRequestStage = requestStageService.save(requestStage);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdRequestStage);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<RequestStage> getById(@PathVariable Long id) {
		RequestStage requestStage = requestStageService.getById(id);
		return ResponseEntity.status(HttpStatus.OK).body(requestStage);
	}
		
	
}
