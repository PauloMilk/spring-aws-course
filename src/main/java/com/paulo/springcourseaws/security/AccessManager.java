package com.paulo.springcourseaws.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.paulo.springcourseaws.domain.Request;
import com.paulo.springcourseaws.domain.User;
import com.paulo.springcourseaws.exception.NotFoundException;
import com.paulo.springcourseaws.repository.UserRepository;
import com.paulo.springcourseaws.service.RequestService;

@Component("accessManager")
public class AccessManager {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RequestService requestService;

	public boolean isOwner(Long id) {

		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<User> result = userRepository.findByEmail(email);
		if (!result.isPresent())
			throw new NotFoundException("There are not user with email: " + email);
		User user = result.get();
		return user.getId() == id;
	}

	public boolean isRequestOwner(Long id) {

		String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<User> result = userRepository.findByEmail(email);
		if (!result.isPresent())
			throw new NotFoundException("There are not user with email: " + email);
		User user = result.get();
		Request request = requestService.getById(id);

		return request.getOwner().getId() == user.getId();
	}

}
