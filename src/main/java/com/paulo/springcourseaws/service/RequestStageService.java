package com.paulo.springcourseaws.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.paulo.springcourseaws.domain.RequestStage;
import com.paulo.springcourseaws.exception.NotFoundException;
import com.paulo.springcourseaws.model.PageModel;
import com.paulo.springcourseaws.model.PageRequestModel;
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
	
	public PageModel<RequestStage> listAllByRequestId(Long id, PageRequestModel pageRequestModel) {
		Pageable pageable = PageRequest.of(pageRequestModel.getPage(), pageRequestModel.getSize());		
		Page<RequestStage> page= requestStageRepository.findAllByRequestId(id, pageable);
		PageModel<RequestStage> requestsStages = new PageModel<RequestStage>((int)page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
		return requestsStages;
	}
	
	public RequestStage getById(Long id) {
		Optional<RequestStage> result = requestStageRepository.findById(id);
		return result.orElseThrow(() -> new NotFoundException("There are not request stage with id = "+id));
	}
	
	
}
