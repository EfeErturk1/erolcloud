package com.erolcloud.erolcloud.response;

import java.time.LocalDateTime;

public class LectureResponse {
    private final Long id;

    private final LocalDateTime lectureStartDate;

    private final LocalDateTime lectureEndDate;

    private final CourseResponse course;

    public LectureResponse(Long lectureId, LocalDateTime lectureStartDate, LocalDateTime lectureEndDate, CourseResponse course) {
        this.id = lectureId;
        this.lectureStartDate = lectureStartDate;
        this.lectureEndDate = lectureEndDate;
        this.course = course;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getLectureStartDate() {
        return lectureStartDate;
    }

    public LocalDateTime getLectureEndDate() {
        return lectureEndDate;
    }

    public CourseResponse getCourse() {
        return course;
    }
}
