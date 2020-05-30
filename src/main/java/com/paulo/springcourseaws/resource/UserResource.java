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
import com.paulo.springcourseaws.domain.User;
import com.paulo.springcourseaws.dto.UserLoginDTO;
import com.paulo.springcourseaws.service.RequestService;
import com.paulo.springcourseaws.service.UserService;

@RestController
@RequestMapping("/users")
public class UserResource {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RequestService requestService;
	
	@PostMapping
	public ResponseEntity<User> save(@RequestBody User user) {
		User createdUser = userService.save(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
		user.setId(id);
		User updatedUser = userService.update(user);
		return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getById(@PathVariable Long id) {
		User user = userService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}
	
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.findAll();
		return ResponseEntity.status(HttpStatus.OK).body(users);
	}
	
	@PostMapping("/login")
	public ResponseEntity<User> login(@RequestBody UserLoginDTO user) {
		User loggedUser = userService.login(user.getEmail(), user.getPassword());
		return ResponseEntity.status(HttpStatus.OK).body(loggedUser);
	}
	
	@GetMapping("/{id}/requests")
	public ResponseEntity<List<Request>> login(@PathVariable Long id) {
		List<Request> requests = requestService.listAllByOwnerId(id);
		return ResponseEntity.status(HttpStatus.OK).body(requests);
	}
	
	
}
