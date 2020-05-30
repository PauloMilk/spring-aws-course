package com.paulo.springcourseaws.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.paulo.springcourseaws.domain.Request;
import com.paulo.springcourseaws.domain.enums.RequestState;
import com.paulo.springcourseaws.exception.NotFoundException;
import com.paulo.springcourseaws.model.PageModel;
import com.paulo.springcourseaws.model.PageRequestModel;
import com.paulo.springcourseaws.repository.RequestRepository;

@Service
public class RequestService {
	
	@Autowired
	private RequestRepository requestRepository;

	public Request save(Request request) {
		request.setState(RequestState.OPEN);
		request.setCriationDate(new Date());
		Request created = requestRepository.save(request);
		return created;
	}
	
	public Request update(Request request) {		
		Request updated = requestRepository.save(request);
		return updated;
	}
	
	public Request getById(Long id) {		
		Optional<Request> result = requestRepository.findById(id);
		return result.orElseThrow(() -> new NotFoundException("There are not request with id = "+id));
	}
	
	
	public PageModel<Request> listAll(PageRequestModel pageRequestModel) {
		Pageable pageable = PageRequest.of(pageRequestModel.getPage(), pageRequestModel.getSize());
		Page<Request> page = requestRepository.findAll(pageable);
		PageModel<Request> requests = new PageModel<Request>((int) page.getTotalElements(), page.getSize(),page.getTotalPages(), page.getContent());
		return requests;
	}
	
	public PageModel<Request> listAllByOwnerId(Long ownerId, PageRequestModel pageRequestModel) {
		Pageable pageable = PageRequest.of(pageRequestModel.getPage(), pageRequestModel.getSize());
		Page<Request> page = requestRepository.findAllByOwnerId(ownerId, pageable);
		PageModel<Request> requests = new PageModel<>((int) page.getTotalPages(), page.getSize(), page.getTotalPages(), page.getContent());
		return requests;
	}

}
