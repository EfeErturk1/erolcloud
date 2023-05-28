package com.erolcloud.erolcloud.controller;

import com.erolcloud.erolcloud.request.AttendLectureRequest;
import com.erolcloud.erolcloud.request.CourseEnrollRequest;
import com.erolcloud.erolcloud.response.LectureResponse;
import com.erolcloud.erolcloud.response.CourseResponse;
import com.erolcloud.erolcloud.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/{studentId}/attendances")
    public ResponseEntity<List<LectureResponse>> getAttendances(@PathVariable Long studentId) {
        return new ResponseEntity<>(studentService.getAttendances(studentId), HttpStatus.OK);
    }

    @GetMapping("/{studentId}/enrollments")
    public ResponseEntity<List<CourseResponse>> getEnrolledCourses(@PathVariable Long studentId) {
        return new ResponseEntity<>(studentService.getEnrolledCourses(studentId), HttpStatus.OK);
    }

    @GetMapping("/{studentId}/lectures/today")
    public ResponseEntity<List<LectureResponse>> getCurrentDayLectures(@PathVariable Long studentId) {
        return new ResponseEntity<>(studentService.getCurrentDayLectures(studentId), HttpStatus.OK);
    }

    @GetMapping("/{studentId}/lectures/current")
    public ResponseEntity<LectureResponse> getCurrentTimeSlotLectures(@PathVariable Long studentId) {
        LectureResponse lectureResponse = studentService.getCurrentTimeSlotLectures(studentId);
        if (lectureResponse == null) {
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity.ok(lectureResponse);
        }
    }

    @PutMapping("/enrollments")
    public ResponseEntity<CourseResponse> enrollCourse(@Valid @RequestBody CourseEnrollRequest courseEnrollRequest) {
        return new ResponseEntity(studentService.enrollCourse(courseEnrollRequest), HttpStatus.OK);
    }

    @PostMapping("/attendances")
    public ResponseEntity<LectureResponse> attendLecture(@Valid @RequestBody AttendLectureRequest attendLectureRequest) {
        return new ResponseEntity<>(studentService.attendLecture(attendLectureRequest), HttpStatus.CREATED);
    }
}
