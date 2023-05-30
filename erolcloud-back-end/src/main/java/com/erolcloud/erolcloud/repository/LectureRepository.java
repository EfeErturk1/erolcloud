package com.erolcloud.erolcloud.repository;

import com.erolcloud.erolcloud.entity.Course;
import com.erolcloud.erolcloud.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
    List<Lecture> findByCourse(Course course);

    List<Lecture> findByCourseIn(List<Course> courses);

    List<Lecture> findAllByStartDateBetween(LocalDateTime startTime, LocalDateTime endTime);
}
