package com.erolcloud.erolcloud.service;

import com.erolcloud.erolcloud.entity.Course;
import com.erolcloud.erolcloud.entity.Instructor;
import com.erolcloud.erolcloud.exception.EntityNotFoundException;
import com.erolcloud.erolcloud.repository.CourseRepository;
import com.erolcloud.erolcloud.repository.InstructorRepository;
import com.erolcloud.erolcloud.repository.StudentRepository;
import com.erolcloud.erolcloud.response.CourseResponse;
import com.erolcloud.erolcloud.response.TimeSlotResponse;
import com.erolcloud.erolcloud.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR', 'STUDENT')")
    public List<CourseResponse> getCourses() {
        return courseRepository.findAll().stream().map(CourseService::getCourseResponse).collect(Collectors.toList());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR', 'STUDENT')")
    public UserResponse getInstructor(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course with ID " + courseId + " not found."));
        for (Instructor instructor : instructorRepository.findAll()) {
            if (instructor.getTeachings().contains(course)) {
                return new UserResponse(instructor.getId(), instructor.getEmail(), instructor.getName());
            }
        }
        throw new EntityNotFoundException("This course does not have an instructor!");
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'INSTRUCTOR', 'STUDENT')")
    public CourseResponse getCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course with ID " + courseId + " not found."));
        return getCourseResponse(course);
    }

    static CourseResponse getCourseResponse(Course course) {
        List<TimeSlotResponse> timeSlotResponses = course.getTimeSlots().stream()
                .map(timeslot -> new TimeSlotResponse(timeslot.getId(), DayOfWeek.of(timeslot.getDayOfWeek()).name(),
                        timeslot.getStartTime(), timeslot.getEndTime()))
                .collect(Collectors.toList());
        return new CourseResponse(course.getId(), course.getSection(), course.getCode(), course.getName(), timeSlotResponses);
    }

    public String getCourseTimeSlots(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course with ID " + courseId + " not found."));
        return course.getTimeSlots().stream()
                .map(timeslot -> DayOfWeek.of(timeslot.getDayOfWeek()).name() + " " + timeslot.getStartTime() + " - " + timeslot.getEndTime())
                .collect(Collectors.joining(", "));
    }
}
