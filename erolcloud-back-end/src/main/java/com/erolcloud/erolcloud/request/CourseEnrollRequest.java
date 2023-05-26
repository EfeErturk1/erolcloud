package com.erolcloud.erolcloud.request;

import javax.validation.constraints.Positive;

public class CourseEnrollRequest {
    @Positive
    private Long studentId;

    @Positive
    private Long courseId;

    public Long getStudentId() {
        return studentId;
    }

    public Long getCourseId() {
        return courseId;
    }
}
