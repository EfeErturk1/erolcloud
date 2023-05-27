package com.erolcloud.erolcloud.controller;

import com.erolcloud.erolcloud.response.AttendanceResponse;
import com.erolcloud.erolcloud.response.StudentResponse;
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
    public ResponseEntity<List<StudentResponse>> getCourseStudents(@PathVariable Long instructorId,
                                                                   @PathVariable Long courseId) {
        return new ResponseEntity<>(instructorService.getCourseStudents(instructorId, courseId), HttpStatus.OK);
    }

    @GetMapping("/{instructorId}/courses/{courseId}/attendances")
    public ResponseEntity<List<AttendanceResponse>> getCourseAttendances(@PathVariable Long instructorId,
                                                                         @PathVariable Long courseId) {
        return new ResponseEntity<>(instructorService.getCourseAttendances(instructorId, courseId), HttpStatus.OK);
    }
}
