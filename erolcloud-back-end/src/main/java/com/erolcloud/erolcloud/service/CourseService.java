package com.erolcloud.erolcloud.service;

import com.erolcloud.erolcloud.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;


}
