package com.erolcloud.erolcloud.controller;

import com.erolcloud.erolcloud.response.CourseResponse;
import com.erolcloud.erolcloud.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<List<CourseResponse>> getCourses() {
        return new ResponseEntity<>(courseService.getCourses(), HttpStatus.OK);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseResponse> getCourse(@PathVariable Long courseId) {
        return new ResponseEntity<>(courseService.getCourse(courseId), HttpStatus.OK);
    }

    @GetMapping("/{courseId}/time-slots")
    public ResponseEntity<String> getCourseTimeSlots(@PathVariable Long courseId) {
        return new ResponseEntity<>(courseService.getCourseTimeSlots(courseId), HttpStatus.OK);
    }


}
