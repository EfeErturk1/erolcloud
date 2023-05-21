package com.erolcloud.erolcloud;

import com.erolcloud.erolcloud.entity.Role;
import com.erolcloud.erolcloud.entity.Student;
import com.erolcloud.erolcloud.repository.RoleRepository;
import com.erolcloud.erolcloud.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

@SpringBootApplication
public class ErolcloudApplication {
	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(ErolcloudApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(RoleRepository roleRepository, StudentRepository studentRepository) {
		return args -> {
			if (!roleRepository.existsByAuthority(Role.Authority.ADMIN)) {
				roleRepository.save(new Role(Role.Authority.ADMIN));
				roleRepository.save(new Role(Role.Authority.INSTRUCTOR));
				roleRepository.save(new Role(Role.Authority.STUDENT));
				studentRepository.save(new Student("d.kantarcioglu@ug.bilkent.edu.tr", "Doruk Kantarcioglu", passwordEncoder.encode("12345678"), Collections.singleton(roleRepository.findByAuthority(Role.Authority.STUDENT).get())));
			}
		};
	}
}
