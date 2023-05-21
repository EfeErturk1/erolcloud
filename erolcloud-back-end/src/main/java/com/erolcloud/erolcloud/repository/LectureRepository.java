package com.erolcloud.erolcloud.repository;

import com.erolcloud.erolcloud.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
}
