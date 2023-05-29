package com.erolcloud.erolcloud;

import com.erolcloud.erolcloud.entity.*;
import com.erolcloud.erolcloud.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class ErolcloudApplication {
	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(ErolcloudApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(RoleRepository roleRepository, StudentRepository studentRepository, TimeSlotRepository timeSlotRepository, CourseRepository courseRepository, LectureRepository lectureRepository, InstructorRepository instructorRepository) {
		return args -> {
			if (!roleRepository.existsByAuthority(Role.Authority.ADMIN)) {
				roleRepository.save(new Role(Role.Authority.ADMIN));
				roleRepository.save(new Role(Role.Authority.INSTRUCTOR));
				roleRepository.save(new Role(Role.Authority.STUDENT));
				studentRepository.save(new Student("d.kantarcioglu@ug.bilkent.edu.tr", "Doruk Kantarcioglu", passwordEncoder.encode("12345678"), Collections.singleton(roleRepository.findByAuthority(Role.Authority.STUDENT).get())));
				Instructor instructor1 = instructorRepository.save(new Instructor("dolak@cs.bilkent.edu.tr", "Erhan Dolak", passwordEncoder.encode("12345678"), Collections.singleton(roleRepository.findByAuthority(Role.Authority.INSTRUCTOR).get())));
				for (int i = 1; i < 6; i++) {
					timeSlotRepository.save(new TimeSlot(i, LocalTime.of(8, 30), LocalTime.of(10, 20)));
					timeSlotRepository.save(new TimeSlot(i, LocalTime.of(10, 30), LocalTime.of(12, 20)));
					timeSlotRepository.save(new TimeSlot(i, LocalTime.of(13, 30), LocalTime.of(15, 20)));
					timeSlotRepository.save(new TimeSlot(i, LocalTime.of(15, 30), LocalTime.of(17, 20)));
				}
				List<TimeSlot> timeSlots1 = timeSlotRepository.findAllById(List.of(1L, 6L));
				List<TimeSlot> timeSlots2 = timeSlotRepository.findAllById(List.of(2L, 7L));
				Course course1 = courseRepository.save(new Course(1, "CS-342", "Operating Systems", timeSlots1));
				Course course2 = courseRepository.save(new Course(2, "CS-342", "Operating Systems", timeSlots2));
				lectureRepository.save(new Lecture(course1, LocalDateTime.of(LocalDate.now(), LocalTime.of(15, 30)), LocalDateTime.of(LocalDate.now(), LocalTime.of(17,20))));
				lectureRepository.save(new Lecture(course1, LocalDateTime.of(LocalDate.now().plusDays(1), course1.getTimeSlots().get(0).getStartTime()), LocalDateTime.of(LocalDate.now().plusDays(1), course1.getTimeSlots().get(0).getEndTime())));
				lectureRepository.save(new Lecture(course1, LocalDateTime.of(LocalDate.now(), course1.getTimeSlots().get(0).getStartTime()), LocalDateTime.of(LocalDate.now(), course1.getTimeSlots().get(0).getEndTime())));
				lectureRepository.save(new Lecture(course1, LocalDateTime.of(LocalDate.now().plusDays(2), course1.getTimeSlots().get(1).getStartTime()), LocalDateTime.of(LocalDate.now().plusDays(2), course1.getTimeSlots().get(1).getEndTime())));
				List<Course> instructor1Courses = instructor1.getTeachings();
				instructor1Courses.add(course1);
				instructor1.setTeachings(instructor1Courses);
				instructorRepository.save(instructor1);
			}
		};
	}
}
