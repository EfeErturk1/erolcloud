package com.erolcloud.erolcloud.repository;

import com.erolcloud.erolcloud.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
