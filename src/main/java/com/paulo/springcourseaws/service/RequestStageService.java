package com.paulo.springcourseaws.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paulo.springcourseaws.domain.RequestStage;
import com.paulo.springcourseaws.domain.enums.RequestState;
import com.paulo.springcourseaws.exception.NotFoundException;
import com.paulo.springcourseaws.repository.RequestRepository;
import com.paulo.springcourseaws.repository.RequestStageRepository;

@Service
public class RequestStageService {

	@Autowired
	private RequestStageRepository requestStageRepository;
	
	@Autowired
	private RequestRepository requestRepository;
	
	public RequestStage save(RequestStage requestStage) {
		requestStage.setRealizationDate(new Date());		
		RequestStage created = requestStageRepository.save(requestStage);
		requestRepository.updateStatus(requestStage.getRequest().getId(), requestStage.getState());
		
		return created;
	}
	
	public RequestStage update(RequestStage requestStage) {
		RequestStage updated = requestStageRepository.save(requestStage);
		return updated;
	}
	
	public List<RequestStage> listAllByRequestId(Long id) {
		List<RequestStage> requestsStages = requestStageRepository.findAllByRequestId(id);
		return requestsStages;
	}
	
	public RequestStage getById(Long id) {
		Optional<RequestStage> result = requestStageRepository.findById(id);
		return result.orElseThrow(() -> new NotFoundException("There are not request stage with id = "+id));
	}
	
	
}
