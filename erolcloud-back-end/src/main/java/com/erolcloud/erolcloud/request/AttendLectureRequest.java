package com.erolcloud.erolcloud.request;

import javax.validation.constraints.NotBlank;

public class AttendLectureRequest {
    private Long studentId;

    private Long lectureId;

    @NotBlank
    private String code;

    public AttendLectureRequest(Long studentId, Long lectureId, String code) {
        this.studentId = studentId;
        this.lectureId = lectureId;
        this.code = code;
    }

    public Long getStudentId() {
        return studentId;
    }

    public Long getLectureId() {
        return lectureId;
    }

    public String getCode() {
        return code;
    }
}
