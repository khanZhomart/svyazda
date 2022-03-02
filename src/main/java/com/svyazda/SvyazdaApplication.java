package com.svyazda;


import com.svyazda.entities.Role;
import com.svyazda.entities.User;
import com.svyazda.repositories.UserRepository;
import com.svyazda.services.UserService;
import com.svyazda.enums.Visibility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class SvyazdaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SvyazdaApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner commandLineRunner(UserRepository userRepository, UserService userService) {
		return args -> {

			User rakhman = new User(null, "rakha", "123", Visibility.ALL, new java.sql.Date(new java.util.Date().getTime()), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
			User azhar = new User(null, "azhar", "123", Visibility.ALL, new java.sql.Date(new java.util.Date().getTime()), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));

			userService.save(rakhman);
			userService.save(azhar);

			userService.addRoleToUser("rakha", "ROLE_USER");
			userService.addRoleToUser("azhar", "ROLE_USER");

		};
	}

}
