package com.erolcloud.erolcloud;

import com.erolcloud.erolcloud.entity.*;
import com.erolcloud.erolcloud.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

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
				Student s1 = studentRepository.save(new Student("d.kantarcioglu@ug.bilkent.edu.tr", "Doruk Kantarcioglu", passwordEncoder.encode("12345678"), Collections.singleton(roleRepository.findByAuthority(Role.Authority.STUDENT).get())));
				Student s2 = studentRepository.save(new Student("efe.erturk@ug.bilkent.edu.tr", "Efe Erturk", passwordEncoder.encode("12345678"), Collections.singleton(roleRepository.findByAuthority(Role.Authority.STUDENT).get())));
				Student s3 = studentRepository.save(new Student("cagla.ataoglu@ug.bilkent.edu.tr", "Cagla Ataoglu", passwordEncoder.encode("12345678"), Collections.singleton(roleRepository.findByAuthority(Role.Authority.STUDENT).get())));
				for (int i = 1; i < 6; i++) {
					timeSlotRepository.save(new TimeSlot(i, LocalTime.of(8, 30), LocalTime.of(10, 20)));
					timeSlotRepository.save(new TimeSlot(i, LocalTime.of(10, 30), LocalTime.of(12, 20)));
					timeSlotRepository.save(new TimeSlot(i, LocalTime.of(13, 30), LocalTime.of(15, 20)));
					timeSlotRepository.save(new TimeSlot(i, LocalTime.of(15, 30), LocalTime.of(17, 20)));
				}
				Set<TimeSlot> timeSlots1 = new HashSet<>(timeSlotRepository.findAllById(List.of(0L, 10L)));
				Set<TimeSlot> timeSlots2 = new HashSet<>(timeSlotRepository.findAllById(List.of(3L, 13L)));
				Set<TimeSlot> timeSlots3 = new HashSet<>(timeSlotRepository.findAllById(List.of(2L, 19L)));
				Set<TimeSlot> timeSlots4 = new HashSet<>(timeSlotRepository.findAllById(List.of(1L, 15L)));
				Set<TimeSlot> timeSlots5 = new HashSet<>(timeSlotRepository.findAllById(List.of(4L, 14L)));
				Set<TimeSlot> timeSlots6 = new HashSet<>(timeSlotRepository.findAllById(List.of(5L, 12L)));
				Set<TimeSlot> timeSlots7 = new HashSet<>(timeSlotRepository.findAllById(List.of(6L, 11L)));
				Set<TimeSlot> timeSlots8 = new HashSet<>(timeSlotRepository.findAllById(List.of(7L, 18L)));
				Set<TimeSlot> timeSlots9 = new HashSet<>(timeSlotRepository.findAllById(List.of(8L)));
				Set<TimeSlot> timeSlots10 = new HashSet<>(timeSlotRepository.findAllById(List.of(9L)));
				Course course1 = courseRepository.save(new Course(1, "CS-342", "Operating Systems", timeSlots1));
				Course course2 = courseRepository.save(new Course(2, "CS-342", "Operating Systems", timeSlots2));
				Course course3 = courseRepository.save(new Course(1, "CS-223", "Digital Design", timeSlots3));
				Course course4 = courseRepository.save(new Course(2, "CS-223", "Digital Design", timeSlots4));
				Course course5 = courseRepository.save(new Course(1, "IE-400", "Principles of Engineering Management", timeSlots5));
				Course course6 = courseRepository.save(new Course(2, "IE-400", "Principles of Engineering Management", timeSlots6));
				Course course7 = courseRepository.save(new Course(1, "CS-202", "Fundamental Structures of Computer Science II", timeSlots7));
				Course course8 = courseRepository.save(new Course(1, "CS-201", "Fundamental Structures of Computer Science I", timeSlots8));
				Course course9 = courseRepository.save(new Course(1, "CS-491", "Senior Design Project I", timeSlots9));
				Course course10 = courseRepository.save(new Course(1, "GE-301", "Science Technology and Society", timeSlots10));
				Instructor instructor1 = instructorRepository.save(new Instructor("dolak@cs.bilkent.edu.tr", "Erhan Dolak", passwordEncoder.encode("12345678"), Collections.singleton(roleRepository.findByAuthority(Role.Authority.INSTRUCTOR).get())));
				Instructor instructor2 = instructorRepository.save(new Instructor("saksoy@cs.bilkent.edu.tr", "Selim Aksoy", passwordEncoder.encode("12345678"), Collections.singleton(roleRepository.findByAuthority(Role.Authority.INSTRUCTOR).get())));
				Instructor instructor3 = instructorRepository.save(new Instructor("ozcan@cs.bilkent.edu.tr", "Ozcan Ozturk", passwordEncoder.encode("12345678"), Collections.singleton(roleRepository.findByAuthority(Role.Authority.INSTRUCTOR).get())));
				Instructor instructor4 = instructorRepository.save(new Instructor("korpe@cs.bilkent.edu.tr", "Ibrahim Korpeoglu", passwordEncoder.encode("12345678"), Collections.singleton(roleRepository.findByAuthority(Role.Authority.INSTRUCTOR).get())));
				Instructor instructor5 = instructorRepository.save(new Instructor("taghi.khaniyev@bilkent.edu.tr", "Tagi Hanalioglu", passwordEncoder.encode("12345678"), Collections.singleton(roleRepository.findByAuthority(Role.Authority.INSTRUCTOR).get())));
				Instructor instructor6 = instructorRepository.save(new Instructor("emineonculer@bilkent.edu.tr", "Emine Onculer", passwordEncoder.encode("12345678"), Collections.singleton(roleRepository.findByAuthority(Role.Authority.INSTRUCTOR).get())));
				List<Course> instructor1Courses = instructor1.getTeachings();
				instructor1Courses.add(course9);
				instructor1.setTeachings(instructor1Courses);
				instructorRepository.save(instructor1);
				List<Course> instructor2Courses = instructor2.getTeachings();
				instructor2Courses.add(course7);
				instructor2Courses.add(course8);
				instructor2.setTeachings(instructor2Courses);
				instructorRepository.save(instructor2);
				List<Course> instructor3Courses = instructor3.getTeachings();
				instructor3Courses.add(course1);
				instructor3Courses.add(course3);
				instructor3.setTeachings(instructor3Courses);
				instructorRepository.save(instructor3);
				List<Course> instructor4Courses = instructor4.getTeachings();
				instructor4Courses.add(course2);
				instructor4Courses.add(course4);
				instructor4.setTeachings(instructor4Courses);
				instructorRepository.save(instructor4);
				List<Course> instructor5Courses = instructor5.getTeachings();
				instructor5Courses.add(course5);
				instructor5Courses.add(course6);
				instructor5.setTeachings(instructor5Courses);
				instructorRepository.save(instructor5);
				List<Course> instructor6Courses = instructor6.getTeachings();
				instructor6Courses.add(course10);
				instructor6.setTeachings(instructor6Courses);
				instructorRepository.save(instructor6);
				for (Course course : courseRepository.findAll()) {
					for (TimeSlot timeSlot : course.getTimeSlots()) {
						LocalDateTime start = LocalDateTime.of(LocalDate.now(), timeSlot.getStartTime());
						LocalDateTime end = LocalDateTime.of(LocalDate.now(), timeSlot.getEndTime());
						lectureRepository.save(new Lecture(course,
								start.with(TemporalAdjusters.next(DayOfWeek.of(timeSlot.getDayOfWeek()))),
								end.with(TemporalAdjusters.next(DayOfWeek.of(timeSlot.getDayOfWeek())))));
					}
				}
				s1.setCourses(List.of(course1, course4));
				studentRepository.save(s1);
				s2.setCourses(List.of(course2, course3));
				studentRepository.save(s2);
				s3.setCourses(List.of(course3, course5, course9));
				studentRepository.save(s3);
				List<Lecture> lectures = new ArrayList<>();
				for (TimeSlot timeSlot : course1.getTimeSlots()) {
					LocalDateTime start = LocalDateTime.of(LocalDate.now().minusDays(8), timeSlot.getStartTime());
					LocalDateTime end = LocalDateTime.of(LocalDate.now().minusDays(8), timeSlot.getEndTime());
					lectures.add(lectureRepository.save(new Lecture(course1,
							start.with(TemporalAdjusters.next(DayOfWeek.of(timeSlot.getDayOfWeek()))),
							end.with(TemporalAdjusters.next(DayOfWeek.of(timeSlot.getDayOfWeek()))))));
				}
				for (TimeSlot timeSlot : course4.getTimeSlots()) {
					LocalDateTime start = LocalDateTime.of(LocalDate.now().minusDays(8), timeSlot.getStartTime());
					LocalDateTime end = LocalDateTime.of(LocalDate.now().minusDays(8), timeSlot.getEndTime());
					lectures.add(lectureRepository.save(new Lecture(course4,
							start.with(TemporalAdjusters.next(DayOfWeek.of(timeSlot.getDayOfWeek()))),
							end.with(TemporalAdjusters.next(DayOfWeek.of(timeSlot.getDayOfWeek()))))));
				}
				s1.setAttendances(lectures);
				studentRepository.save(s1);

				lectures = new ArrayList<>();
				for (TimeSlot timeSlot : course2.getTimeSlots()) {
					LocalDateTime start = LocalDateTime.of(LocalDate.now().minusDays(8), timeSlot.getStartTime());
					LocalDateTime end = LocalDateTime.of(LocalDate.now().minusDays(8), timeSlot.getEndTime());
					lectures.add(lectureRepository.save(new Lecture(course2,
							start.with(TemporalAdjusters.next(DayOfWeek.of(timeSlot.getDayOfWeek()))),
							end.with(TemporalAdjusters.next(DayOfWeek.of(timeSlot.getDayOfWeek()))))));
				}
				for (TimeSlot timeSlot : course3.getTimeSlots()) {
					LocalDateTime start = LocalDateTime.of(LocalDate.now().minusDays(8), timeSlot.getStartTime());
					LocalDateTime end = LocalDateTime.of(LocalDate.now().minusDays(8), timeSlot.getEndTime());
					lectures.add(lectureRepository.save(new Lecture(course3,
							start.with(TemporalAdjusters.next(DayOfWeek.of(timeSlot.getDayOfWeek()))),
							end.with(TemporalAdjusters.next(DayOfWeek.of(timeSlot.getDayOfWeek()))))));
				}
				s2.setAttendances(lectures);
				studentRepository.save(s2);

				lectures = new ArrayList<>();
				for (TimeSlot timeSlot : course3.getTimeSlots()) {
					LocalDateTime start = LocalDateTime.of(LocalDate.now().minusDays(8), timeSlot.getStartTime());
					LocalDateTime end = LocalDateTime.of(LocalDate.now().minusDays(8), timeSlot.getEndTime());
					lectures.add(lectureRepository.save(new Lecture(course3,
							start.with(TemporalAdjusters.next(DayOfWeek.of(timeSlot.getDayOfWeek()))),
							end.with(TemporalAdjusters.next(DayOfWeek.of(timeSlot.getDayOfWeek()))))));
				}
				for (TimeSlot timeSlot : course5.getTimeSlots()) {
					LocalDateTime start = LocalDateTime.of(LocalDate.now().minusDays(8), timeSlot.getStartTime());
					LocalDateTime end = LocalDateTime.of(LocalDate.now().minusDays(8), timeSlot.getEndTime());
					lectures.add(lectureRepository.save(new Lecture(course5,
							start.with(TemporalAdjusters.next(DayOfWeek.of(timeSlot.getDayOfWeek()))),
							end.with(TemporalAdjusters.next(DayOfWeek.of(timeSlot.getDayOfWeek()))))));
				}
				for (TimeSlot timeSlot : course9.getTimeSlots()) {
					LocalDateTime start = LocalDateTime.of(LocalDate.now().minusDays(8), timeSlot.getStartTime());
					LocalDateTime end = LocalDateTime.of(LocalDate.now().minusDays(8), timeSlot.getEndTime());
					lectures.add(lectureRepository.save(new Lecture(course9,
							start.with(TemporalAdjusters.next(DayOfWeek.of(timeSlot.getDayOfWeek()))),
							end.with(TemporalAdjusters.next(DayOfWeek.of(timeSlot.getDayOfWeek()))))));
				}
				s3.setAttendances(lectures);
				studentRepository.save(s3);

				lectureRepository.save(new Lecture(course10, LocalDateTime.of(LocalDate.now(), LocalTime.of(22, 30)), LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(8,20))));
				lectureRepository.save(new Lecture(course10, LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.of(22, 30)), LocalDateTime.of(LocalDate.now().plusDays(2), LocalTime.of(8,20))));
			}
		};
	}
}
