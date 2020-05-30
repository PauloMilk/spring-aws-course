package com.paulo.springcourseaws.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paulo.springcourseaws.domain.Request;
import com.paulo.springcourseaws.domain.RequestStage;
import com.paulo.springcourseaws.service.RequestService;
import com.paulo.springcourseaws.service.RequestStageService;

@RestController
@RequestMapping(value = "requests")
public class RequestResource {
	
	@Autowired
	private RequestService requestService;
	
	@Autowired
	private RequestStageService requestStageService;
	
	@PostMapping
	public ResponseEntity<Request> save(@RequestBody Request request) {
		Request createdRequest = requestService.save(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(request);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Request> update(@PathVariable Long id, @RequestBody Request request) {
		request.setId(id);
		Request updatedRequest = requestService.update(request);
		return ResponseEntity.status(HttpStatus.OK).body(updatedRequest);	
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Request> getById(@PathVariable Long id) {
		Request request = requestService.getById(id);
		return ResponseEntity.status(HttpStatus.OK).body(request);	
	}
	
	
	@GetMapping
	public ResponseEntity<List<Request>> getAll() {
		List<Request> requests = requestService.listAll();
		return ResponseEntity.status(HttpStatus.OK).body(requests);	
	}
	
	@GetMapping("/{id}/request-stage")
	public ResponseEntity<List<RequestStage>> getAllByRequestId(@PathVariable Long id) {
		List<RequestStage> stages = requestStageService.listAllByRequestId(id);
		return ResponseEntity.status(HttpStatus.OK).body(stages);
	}
	

}
