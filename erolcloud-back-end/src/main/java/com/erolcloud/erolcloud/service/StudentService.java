package com.erolcloud.erolcloud.service;

import com.erolcloud.erolcloud.entity.Course;
import com.erolcloud.erolcloud.entity.Lecture;
import com.erolcloud.erolcloud.entity.Student;
import com.erolcloud.erolcloud.exception.EntityAlreadyExistsException;
import com.erolcloud.erolcloud.exception.EntityNotFoundException;
import com.erolcloud.erolcloud.repository.CourseRepository;
import com.erolcloud.erolcloud.repository.LectureRepository;
import com.erolcloud.erolcloud.repository.StudentRepository;
import com.erolcloud.erolcloud.request.CourseEnrollRequest;
import com.erolcloud.erolcloud.response.LectureResponse;
import com.erolcloud.erolcloud.response.CourseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @PreAuthorize("hasAuthority('STUDENT') and #courseEnrollRequest.studentId == authentication.principal.id")
    public CourseResponse enrollCourse(CourseEnrollRequest courseEnrollRequest) {
        Student student = studentRepository.findById(courseEnrollRequest.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Student with ID " + courseEnrollRequest.getStudentId() + " not found."));
        Course course = courseRepository.findById(courseEnrollRequest.getCourseId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Course with ID " + courseEnrollRequest.getCourseId() + " not found."));
        List<Course> courses = student.getCourses();
        if (courses.contains(course)) {
            throw new EntityAlreadyExistsException("Student with ID " + student.getId()
                    + " already enrolled to the course with ID " + course.getId() + ".");
        }
        courses.add(course);
        student.setCourses(courses);
        studentRepository.save(student);
        return CourseService.getCourseResponse(course);
    }

    @PreAuthorize("hasAuthority('STUDENT') and #studentId == authentication.principal.id")
    public List<CourseResponse> getEnrolledCourses(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student with ID " + studentId + " not found."));
        return student.getCourses().stream().map(CourseService::getCourseResponse).collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('STUDENT') and #studentId == authentication.principal.id")
    public List<LectureResponse> getAttendances(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student with ID " + studentId + " not found."));
        return student.getAttendances().stream()
                .map(lecture -> new LectureResponse(lecture.getId(), lecture.getStartDate(), lecture.getEndDate(),
                        CourseService.getCourseResponse(lecture.getCourse())))
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('STUDENT') and #studentId == authentication.principal.id")
    public List<LectureResponse> getCurrentDayLectures(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student with ID " + studentId + " not found."));
        List<Lecture> currentDayLectures = lectureRepository
                .findAllByStartDateBetween(
                        LocalDateTime.of(LocalDate.now(), LocalTime.MIN),
                        LocalDateTime.of(LocalDate.now(), LocalTime.MAX)
                );
        List<Lecture> studentCurrentDayLectures = new ArrayList<>();
        for (Course course : student.getCourses()) {
            for (Lecture lecture : currentDayLectures) {
                if (lecture.getCourse().getId().equals(course.getId())) {
                    studentCurrentDayLectures.add(lecture);
                }
            }
        }
        return studentCurrentDayLectures.stream()
                .map(lecture -> new LectureResponse(lecture.getId(), lecture.getStartDate(), lecture.getEndDate(),
                        CourseService.getCourseResponse(lecture.getCourse())))
                .collect(Collectors.toList());
    }
}
