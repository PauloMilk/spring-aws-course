package com.paulo.springcourseaws.resource;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paulo.springcourseaws.domain.Request;
import com.paulo.springcourseaws.domain.User;
import com.paulo.springcourseaws.dto.UserLoginDTO;
import com.paulo.springcourseaws.dto.UserLoginResponseDTO;
import com.paulo.springcourseaws.dto.UserSaveDTO;
import com.paulo.springcourseaws.dto.UserUpdateDTO;
import com.paulo.springcourseaws.dto.UserUpdateRoleDTO;
import com.paulo.springcourseaws.model.PageModel;
import com.paulo.springcourseaws.model.PageRequestModel;
import com.paulo.springcourseaws.security.AccessManager;
import com.paulo.springcourseaws.security.JwtManager;
import com.paulo.springcourseaws.service.RequestService;
import com.paulo.springcourseaws.service.UserService;

@RestController
@RequestMapping("/users")
public class UserResource {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RequestService requestService;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtManager jwtManager;
	
	@Autowired
	private AccessManager accessManager;
	
	@Secured({"ROLE_ADMINISTRADOR"})
	@PostMapping
	public ResponseEntity<User> save(@RequestBody @Valid UserSaveDTO user) {
		User createdUser = userService.save(user.transformToUser());
		return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
	}
	
	
	@PreAuthorize("@accessManager.isOwner(#id)")
	@PutMapping("/{id}")
	public ResponseEntity<User> update(@PathVariable Long id, @RequestBody @Valid UserUpdateDTO user) {
		User updatedUser = user.transformToUser();
		updatedUser.setId(id);
		updatedUser = userService.update(updatedUser);
		return ResponseEntity.ok(updatedUser);
	}


	@GetMapping("/{id}")
	public ResponseEntity<User> getById(@PathVariable Long id) {
		User user = userService.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}
	
	@GetMapping
	public ResponseEntity<PageModel<User>> getAllUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		PageRequestModel pageRequestModel = new PageRequestModel(page,size);
		PageModel<User> users = userService.findAllPageModel(pageRequestModel);
		return ResponseEntity.status(HttpStatus.OK).body(users);
	}
	
	@PostMapping("/login")
	public ResponseEntity<UserLoginResponseDTO> login(@RequestBody @Valid UserLoginDTO user) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
		Authentication auth =  authManager.authenticate(token);
		
		SecurityContextHolder.getContext().setAuthentication(auth);
		
		org.springframework.security.core.userdetails.User userSpring = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
		String email = userSpring.getUsername();
		List<String> roles =userSpring.getAuthorities().stream().map(authority -> authority.getAuthority()).collect(Collectors.toList()); 
		
		UserLoginResponseDTO jwt = jwtManager.createToken(email, roles);
		return ResponseEntity.status(HttpStatus.OK).body(jwt);
	}
	
	@GetMapping("/{id}/requests")
	public ResponseEntity<PageModel<Request>> login(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @PathVariable Long id) {
		PageRequestModel pageRequestModel = new PageRequestModel(page,size);
		PageModel<Request> requests = requestService.listAllByOwnerId(id, pageRequestModel);
		return ResponseEntity.status(HttpStatus.OK).body(requests);
	}
	
	@Secured({"ROLE_ADMINISTRADOR"})
	@PatchMapping("{id}/role/")
	public ResponseEntity<?> updateRole(@PathVariable Long id, @RequestBody @Valid UserUpdateRoleDTO user) {
		User updateUser = new User();
		updateUser.setId(id);
		updateUser.setRole(user.getRole());
		userService.updateRole(updateUser);
		
		return ResponseEntity.ok().build();
		
	}
	
	
}
