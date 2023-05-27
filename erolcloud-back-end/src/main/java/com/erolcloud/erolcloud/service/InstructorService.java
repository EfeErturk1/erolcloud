package com.erolcloud.erolcloud.service;

import com.erolcloud.erolcloud.entity.Course;
import com.erolcloud.erolcloud.entity.Instructor;
import com.erolcloud.erolcloud.entity.Lecture;
import com.erolcloud.erolcloud.entity.Student;
import com.erolcloud.erolcloud.exception.CourseNotTaughtByInstructorException;
import com.erolcloud.erolcloud.exception.EntityNotFoundException;
import com.erolcloud.erolcloud.repository.CourseRepository;
import com.erolcloud.erolcloud.repository.InstructorRepository;
import com.erolcloud.erolcloud.repository.LectureRepository;
import com.erolcloud.erolcloud.repository.StudentRepository;
import com.erolcloud.erolcloud.response.AttendanceResponse;
import com.erolcloud.erolcloud.response.LectureResponse;
import com.erolcloud.erolcloud.response.StudentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstructorService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private LectureRepository lectureRepository;

    @PreAuthorize("hasAuthority('INSTRUCTOR') and #instructorId == authentication.principal.id")
    public List<AttendanceResponse> getCourseAttendances(Long instructorId, Long courseId) {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new EntityNotFoundException("Instructor with ID " + instructorId + " not found."));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course with ID " + courseId + " not found."));
        if (!instructor.getTeachings().contains(course)) {
            throw new CourseNotTaughtByInstructorException(
                    "Course with ID " + courseId + " is not taught by instructor with ID " + instructorId + ".");
        }
        List<AttendanceResponse> attendanceResponses = new ArrayList<>();
        List<Lecture> courseLectures = lectureRepository.findByCourse(course);
        for (Student student : course.getStudents()) {
            List<LectureResponse> lectureResponses = new ArrayList<>();
            for (Lecture lecture : courseLectures) {
                if (student.getAttendances().contains(lecture)) {
                    lectureResponses.add(new LectureResponse(lecture.getId(),
                            lecture.getStartDate(), lecture.getEndDate(),
                            CourseService.getCourseResponse(lecture.getCourse())));
                }
            }
            attendanceResponses.add(new AttendanceResponse(
                    new StudentResponse(student.getId(), student.getEmail(), student.getName()), lectureResponses));
        }
        return attendanceResponses;
    }

    @PreAuthorize("hasAuthority('INSTRUCTOR') and #instructorId == authentication.principal.id")
    public List<StudentResponse> getCourseStudents(Long instructorId, Long courseId) {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new EntityNotFoundException("Instructor with ID " + instructorId + " not found."));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course with ID " + courseId + " not found."));
        if (!instructor.getTeachings().contains(course)) {
            throw new CourseNotTaughtByInstructorException(
                    "Course with ID " + courseId + " is not taught by instructor with ID " + instructorId + ".");
        }
        return course.getStudents().stream()
                .map(student -> new StudentResponse(student.getId(), student.getEmail(), student.getName()))
                .collect(Collectors.toList());
    }
}
