package com.erolcloud.erolcloud.response;

import java.util.List;

public class StudentAttendanceResponse {
    private final Long studentId;

    private final List<StudentLectureResponse> lectures;

    public StudentAttendanceResponse(Long studentId, List<StudentLectureResponse> lectures) {
        this.studentId = studentId;
        this.lectures = lectures;
    }

    public Long getStudentId() {
        return studentId;
    }

    public List<StudentLectureResponse> getLectures() {
        return lectures;
    }
}
