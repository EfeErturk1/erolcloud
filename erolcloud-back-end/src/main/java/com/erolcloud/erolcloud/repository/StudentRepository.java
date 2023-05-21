package com.erolcloud.erolcloud.repository;

import com.erolcloud.erolcloud.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
