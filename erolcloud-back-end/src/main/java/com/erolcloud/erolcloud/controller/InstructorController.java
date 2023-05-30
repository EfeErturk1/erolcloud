package com.erolcloud.erolcloud.controller;

import com.erolcloud.erolcloud.response.*;
import com.erolcloud.erolcloud.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/instructors")
public class InstructorController {
    @Autowired
    private InstructorService instructorService;

    @GetMapping("/{instructorId}/courses/{courseId}/students")
    public ResponseEntity<List<UserResponse>> getCourseStudents(@PathVariable Long instructorId,
                                                                @PathVariable Long courseId) {
        return new ResponseEntity<>(instructorService.getCourseStudents(instructorId, courseId), HttpStatus.OK);
    }

    @GetMapping("/{instructorId}/courses/{courseId}/attendances")
    public ResponseEntity<List<AttendanceResponse>> getCourseAttendances(@PathVariable Long instructorId,
                                                                         @PathVariable Long courseId) {
        return new ResponseEntity<>(instructorService.getCourseAttendances(instructorId, courseId), HttpStatus.OK);
    }

    @GetMapping("/{instructorId}/lectures/current")
    public ResponseEntity<InstructorCodeResponse> getCurrentLectureCode(@PathVariable Long instructorId) {
        InstructorCodeResponse response = instructorService.getCurrentLectureCode(instructorId);
        if (response == null) {
            return ResponseEntity.noContent().build();
        }
        else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @GetMapping("/{instructorId}/attendances")
    public ResponseEntity<InstructorAttendanceResponse> getTotalAttendances(@PathVariable Long instructorId) {
        return ResponseEntity.ok(instructorService.getStudentAttendanceRecords(instructorId));
    }

    @GetMapping("/{instructorId}/courses")
    public ResponseEntity<List<CourseResponse>> getCourseStudents(@PathVariable Long instructorId) {
        return new ResponseEntity<>(instructorService.getInstructorCourses(instructorId), HttpStatus.OK);
    }
}
