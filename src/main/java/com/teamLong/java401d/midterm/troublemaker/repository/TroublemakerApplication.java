package com.teamLong.java401d.midterm.troublemaker.repository;

import com.teamLong.java401d.midterm.troublemaker.model.RoleType;
import com.teamLong.java401d.midterm.troublemaker.model.UserAccount;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class TroublemakerApplication {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(TroublemakerApplication.class, args);
	}

	@Bean
	InitializingBean seedDatabase() {
		return () -> {
			roleRepository.save(new RoleType("admin"));
			roleRepository.save(new RoleType("user"));

			UserAccount admin = new UserAccount();
			admin.setUsername("admin@codefellows.com");
			admin.setPassword(encoder.encode("admin"));
			admin.setConfirmPassword((encoder.encode("admin")));
			admin.getRoleTypes().add(roleRepository.findByRole("user"));
			admin.getRoleTypes().add(roleRepository.findByRole("admin"));

			userRepository.save(admin);
		};
	}

}
