package com.erolcloud.erolcloud.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String code;

    public Lecture() {}

    public Lecture(Course course, LocalDateTime startDate, LocalDateTime endDate) {
        this.course = course;
        this.startDate = startDate;
        this.endDate = endDate;
        code = String.format("%06d", (int) (Math.random() * 1000000));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
