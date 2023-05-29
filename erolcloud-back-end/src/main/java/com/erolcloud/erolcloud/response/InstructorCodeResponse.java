package com.erolcloud.erolcloud.response;

public class InstructorCodeResponse {
    final Long lectureId;

    final String code;

    public InstructorCodeResponse(Long lectureId, String code) {
        this.lectureId = lectureId;
        this.code = code;
    }

    public Long getLectureId() {
        return lectureId;
    }

    public String getCode() {
        return code;
    }
}
