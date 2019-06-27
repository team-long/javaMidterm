package com.teamLong.java401d.midterm.troublemaker;

import com.teamLong.java401d.midterm.troublemaker.model.*;
import com.teamLong.java401d.midterm.troublemaker.repository.RoleRepository;
import com.teamLong.java401d.midterm.troublemaker.repository.TicketRepository;
import com.teamLong.java401d.midterm.troublemaker.repository.UserRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class TroublemakerApplication {

	@Autowired
	private TicketRepository ticketRepository;

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
			if(roleRepository.findByRole("admin") == null) {
				roleRepository.save(new RoleType("admin"));
				roleRepository.save(new RoleType("user"));

				UserAccount admin = new UserAccount();
				admin.setUsername("admin@codefellows.com");
				admin.setPassword(encoder.encode("admin"));
				admin.setConfirmPassword((encoder.encode("admin")));
				admin.getRoleTypes().add(roleRepository.findByRole("user"));
				admin.getRoleTypes().add(roleRepository.findByRole("admin"));

				UserAccount adminTwo = new UserAccount();
				adminTwo.setUsername("troublemakeraws@gmail.com");
				adminTwo.setPassword(encoder.encode("admin"));
				adminTwo.setConfirmPassword((encoder.encode("admin")));
				adminTwo.getRoleTypes().add(roleRepository.findByRole("user"));
				adminTwo.getRoleTypes().add(roleRepository.findByRole("admin"));

				userRepository.save(admin);
				userRepository.save(adminTwo);
				Ticket ticket = new Ticket("initial ticker", Severity.HIGH, admin, "Test");
				ticketRepository.save(ticket);
			}

			System.out.println("http://localhost:8080/login");
		};


	}


}