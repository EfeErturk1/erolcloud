package com.erolcloud.erolcloud.service;

import com.erolcloud.erolcloud.repository.InstructorRepository;
import com.erolcloud.erolcloud.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstructorService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private InstructorRepository instructorRepository;


}
