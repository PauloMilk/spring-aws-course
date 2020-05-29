package com.paulo.springcourseaws.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paulo.springcourseaws.domain.Request;
import com.paulo.springcourseaws.domain.enums.RequestState;
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
		return result.get();
	}
	
	
	public List<Request> listAll() {		
		List<Request> requests = requestRepository.findAll();
		return requests;
	}
	
	public List<Request> listAllByOwnerId(Long ownerId) {
		List<Request> requests = requestRepository.findAllByOwnerId(ownerId);
		return requests;
	}

}
