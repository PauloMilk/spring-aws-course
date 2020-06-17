package com.paulo.springcourseaws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.paulo.springcourseaws.domain.User;
import com.paulo.springcourseaws.domain.enums.Role;
import com.paulo.springcourseaws.repository.UserRepository;

@SpringBootApplication
public class SpringCourseAwsApplication extends SpringBootServletInitializer implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringCourseAwsApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SpringCourseAwsApplication.class);
	}
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	public void run(String... args) throws Exception {

		User user = this.userRepository.findByEmail("admin@gmail.com").orElse(new User());
		if(user.getEmail() == null) {
			user = new User(null, "admin", "admin@gmail.com", encoder.encode("123"), Role.ADMINISTRADOR, null, null);
			userRepository.save(user);
		}

		
		User user2 = this.userRepository.findByEmail("usuario@gmail.com").orElse(new User());
		if(user2.getEmail() == null) {
			user2 = new User(null, "usuario", "usuario@gmail.com", encoder.encode("123"), Role.SIMPLE, null, null);
			userRepository.save(user2);
		}
		
		
	}

	
}
