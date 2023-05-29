package com.erolcloud.erolcloud.service;

import com.erolcloud.erolcloud.entity.Course;
import com.erolcloud.erolcloud.entity.Lecture;
import com.erolcloud.erolcloud.entity.Student;
import com.erolcloud.erolcloud.exception.*;
import com.erolcloud.erolcloud.repository.CourseRepository;
import com.erolcloud.erolcloud.repository.LectureRepository;
import com.erolcloud.erolcloud.repository.StudentRepository;
import com.erolcloud.erolcloud.request.AttendLectureRequest;
import com.erolcloud.erolcloud.request.CourseRequest;
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

    @PreAuthorize("hasAuthority('STUDENT') and #courseRequest.studentId == authentication.principal.id")
    public CourseResponse enrollCourse(CourseRequest courseRequest) {
        Student student = studentRepository.findById(courseRequest.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Student with ID " + courseRequest.getStudentId() + " not found."));
        Course course = courseRepository.findById(courseRequest.getCourseId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Course with ID " + courseRequest.getCourseId() + " not found."));
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

    @PreAuthorize("hasAuthority('STUDENT') and #courseRequest.studentId == authentication.principal.id")
    public void unenrollCourse(CourseRequest courseRequest) {
        Student student = studentRepository.findById(courseRequest.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Student with ID " + courseRequest.getStudentId() + " not found."));
        Course course = courseRepository.findById(courseRequest.getCourseId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Course with ID " + courseRequest.getCourseId() + " not found."));
        List<Course> courses = student.getCourses();
        if (!courses.contains(course)) {
            throw new EntityNotFoundException("Student with ID " + student.getId()
                    + " is not enrolled to the course with ID " + course.getId() + ".");
        }
        courses.remove(course);
        student.setCourses(courses);
        studentRepository.save(student);
    }

    @PreAuthorize("hasAuthority('STUDENT') and #attendLectureRequest.studentId == authentication.principal.id")
    public LectureResponse attendLecture(AttendLectureRequest attendLectureRequest) {
        Student student = studentRepository.findById(attendLectureRequest.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Student with ID " + attendLectureRequest.getStudentId() + " not found."));
        Lecture lecture = lectureRepository.findById(attendLectureRequest.getLectureId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Lecture with ID " + attendLectureRequest.getLectureId() + " not found."));
        if (!student.getCourses().contains(lecture.getCourse())) {
            throw new CourseLectureMismatchException("Lecture with ID " + lecture.getId() +
                    " does not belong to student's courses.");
        }
        if (!attendLectureRequest.getCode().equals(lecture.getCode())) {
            throw new LectureCodeMismatchException("Lecture code does not match.");
        }
        if (lecture.getEndDate().isBefore(LocalDateTime.now())) {
            throw new LectureExpiredException("Lecture with ID " + lecture.getId() + " is already done.");
        }
        List<Lecture> studentLectures = student.getAttendances();
        if (studentLectures.contains(lecture)) {
            throw new EntityAlreadyExistsException("Student with ID " + student.getId()
                    + " already attended the lecture with ID " + lecture.getId() + ".");
        }
        studentLectures.add(lecture);
        student.setAttendances(studentLectures);
        studentRepository.save(student);
        return new LectureResponse(lecture.getId(), lecture.getStartDate(), lecture.getEndDate(),
                CourseService.getCourseResponse(lecture.getCourse()));
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
        List<Lecture> currentDayLectures = lectureRepository.findAllByStartDateBetween(
                        LocalDateTime.of(LocalDate.now(), LocalTime.MIN),
                        LocalDateTime.of(LocalDate.now(), LocalTime.MAX));
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

    @PreAuthorize("hasAuthority('STUDENT') and #studentId == authentication.principal.id")
    public LectureResponse getCurrentTimeSlotLectures(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student with ID " + studentId + " not found."));
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
        for (Course course : student.getCourses()) {
            for (Lecture lecture : currentDayLectures) {
                if (lecture.getCourse().getId().equals(course.getId())) {
                    return new LectureResponse(lecture.getId(), lecture.getStartDate(), lecture.getEndDate(),
                            CourseService.getCourseResponse(lecture.getCourse()));
                }
            }
        }
        return null;
    }
}
