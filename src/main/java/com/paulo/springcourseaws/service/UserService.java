package com.paulo.springcourseaws.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.paulo.springcourseaws.domain.User;
import com.paulo.springcourseaws.exception.NotFoundException;
import com.paulo.springcourseaws.model.PageModel;
import com.paulo.springcourseaws.model.PageRequestModel;
import com.paulo.springcourseaws.repository.UserRepository;
import com.paulo.springcourseaws.service.util.HashUtil;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public User save(User user) {
		String hash = HashUtil.getSecureHash(user.getPassword());
		user.setPassword(hash);		
		User createdUser = userRepository.save(user);
		return createdUser;
	}
	
	public User update(User user) {
		String hash = HashUtil.getSecureHash(user.getPassword());
		user.setPassword(hash);
		
		User updatedUser = userRepository.save(user);
		return updatedUser;
	}
	
	public User findById(Long id) {
		Optional<User> result = userRepository.findById(id);
		return result.orElseThrow(() -> new NotFoundException("There are not user with id = "+id));
	}
	
	public List<User> findAll() {
		List<User> users = userRepository.findAll();
		return users;
	}
	
	public PageModel<User> findAllPageModel(PageRequestModel pageRequestModel) {
		Pageable pageable = PageRequest.of(pageRequestModel.getPage(), pageRequestModel.getSize());
		Page<User> page = userRepository.findAll(pageable);
		PageModel<User> pageModel = new PageModel<>((int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent()); 		
		return pageModel;
	}
	
	public User login(String email, String password) {
		String hash = HashUtil.getSecureHash(password);
		Optional<User> result = userRepository.findByEmailAndPassword(email, hash);
		return result.get();
	}

}
