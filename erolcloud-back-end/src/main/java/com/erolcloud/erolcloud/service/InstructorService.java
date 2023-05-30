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
import com.erolcloud.erolcloud.response.*;
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
                    new UserResponse(student.getId(), student.getEmail(), student.getName()), lectureResponses));
        }
        return attendanceResponses;
    }

    @PreAuthorize("hasAuthority('INSTRUCTOR') and #instructorId == authentication.principal.id")
    public List<UserResponse> getCourseStudents(Long instructorId, Long courseId) {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new EntityNotFoundException("Instructor with ID " + instructorId + " not found."));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Course with ID " + courseId + " not found."));
        if (!instructor.getTeachings().contains(course)) {
            throw new CourseNotTaughtByInstructorException(
                    "Course with ID " + courseId + " is not taught by instructor with ID " + instructorId + ".");
        }
        return course.getStudents().stream()
                .map(student -> new UserResponse(student.getId(), student.getEmail(), student.getName()))
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('INSTRUCTOR') and #instructorId == authentication.principal.id")
    public InstructorAttendanceResponse getStudentAttendanceRecords(Long instructorId) {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new EntityNotFoundException("Instructor with ID " + instructorId + " not found."));
        List<Course> instructorCourses = instructor.getTeachings();
        List<Lecture> totalLectures = lectureRepository.findByCourseIn(instructorCourses);
        List<InstructorLectureResponse> lectureResponses = new ArrayList<>();
        for (Lecture lecture: totalLectures) {
            List<InstructorLectureStudentResponse> studentResponses = new ArrayList<>();
            for (Student student : lecture.getCourse().getStudents()) {
                if (student.getAttendances().contains(lecture)) {
                    studentResponses.add(new InstructorLectureStudentResponse(
                            new UserResponse(student.getId(), student.getEmail(), student.getName()), true));
                }
                else {
                    studentResponses.add(new InstructorLectureStudentResponse(
                            new UserResponse(student.getId(), student.getEmail(), student.getName()), false));
                }
            }
            lectureResponses.add(new InstructorLectureResponse(
                    new LectureResponse(lecture.getId(), lecture.getStartDate(), lecture.getEndDate(),
                            CourseService.getCourseResponse(lecture.getCourse())), studentResponses));
        }
        return new InstructorAttendanceResponse(instructorId, lectureResponses);
    }

    @PreAuthorize("hasAuthority('INSTRUCTOR') and #instructorId == authentication.principal.id")
    public List<CourseResponse> getInstructorCourses(Long instructorId) {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new EntityNotFoundException("Instructor with ID " + instructorId + " not found."));
        return instructor.getTeachings().stream().map(CourseService::getCourseResponse).collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('INSTRUCTOR') and #instructorId == authentication.principal.id")
    public InstructorCodeResponse getCurrentLectureCode(Long instructorId) {
        Instructor instructor = instructorRepository.findById(instructorId)
                .orElseThrow(() -> new EntityNotFoundException("Instructor with ID " + instructorId + " not found."));
        List<Lecture> currentDayLectures;
        if (LocalTime.now().isBefore(LocalTime.of(10, 20))) {
            currentDayLectures = lectureRepository.findAllByStartDateBetween(
                    LocalDateTime.of(LocalDate.now(), LocalTime.MIN),
                    LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 20)));
        }
        else if (LocalTime.now().isBefore(LocalTime.of(12, 20))) {
            currentDayLectures = lectureRepository.findAllByStartDateBetween(
                    LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 20)),
                    LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 20)));
        }
        else if (LocalTime.now().isBefore(LocalTime.of(15, 20))) {
            currentDayLectures = lectureRepository.findAllByStartDateBetween(
                    LocalDateTime.of(LocalDate.now(), LocalTime.of(13, 20)),
                    LocalDateTime.of(LocalDate.now(), LocalTime.of(15, 20)));
        }
        else {
            currentDayLectures = lectureRepository.findAllByStartDateBetween(
                    LocalDateTime.of(LocalDate.now(), LocalTime.of(15, 20)),
                    LocalDateTime.of(LocalDate.now(), LocalTime.MAX));
        }
        for (Course course : instructor.getTeachings()) {
            for (Lecture lecture : currentDayLectures) {
                if (lecture.getCourse().getId().equals(course.getId())) {
                    return new InstructorCodeResponse(lecture.getId(), lecture.getCode());
                }
            }
        }
        return null;
    }
}
