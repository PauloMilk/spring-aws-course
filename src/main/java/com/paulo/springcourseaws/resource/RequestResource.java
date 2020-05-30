package com.paulo.springcourseaws.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paulo.springcourseaws.domain.Request;
import com.paulo.springcourseaws.domain.RequestStage;
import com.paulo.springcourseaws.dto.RequestSaveDTO;
import com.paulo.springcourseaws.dto.RequestUpdateDTO;
import com.paulo.springcourseaws.model.PageModel;
import com.paulo.springcourseaws.model.PageRequestModel;
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
	public ResponseEntity<Request> save(@RequestBody @Valid RequestSaveDTO dto) {
		Request createdRequest = dto.transformToRequest();
		createdRequest = requestService.save(createdRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Request> update(@PathVariable Long id, @RequestBody @Valid RequestUpdateDTO dto) {
		Request updatedRequest = dto.transformToRequest();
		updatedRequest.setId(id);
		updatedRequest = requestService.update(updatedRequest);
		return ResponseEntity.status(HttpStatus.OK).body(updatedRequest);	
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Request> getById(@PathVariable Long id) {
		Request request = requestService.getById(id);
		return ResponseEntity.status(HttpStatus.OK).body(request);	
	}
	
	
	@GetMapping
	public ResponseEntity<PageModel<Request>> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		PageRequestModel pageRequestModel = new PageRequestModel(page,size);
		PageModel<Request> requests = requestService.listAll(pageRequestModel);
		return ResponseEntity.status(HttpStatus.OK).body(requests);	
	}
	
	@GetMapping("/{id}/request-stage")
	public ResponseEntity<PageModel<RequestStage>> getAllByRequestId(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @PathVariable Long id) {
		PageRequestModel pageRequestModel = new PageRequestModel(page,size);
		PageModel<RequestStage> stages = requestStageService.listAllByRequestId(id, pageRequestModel);
		return ResponseEntity.status(HttpStatus.OK).body(stages);
	}
	

}
