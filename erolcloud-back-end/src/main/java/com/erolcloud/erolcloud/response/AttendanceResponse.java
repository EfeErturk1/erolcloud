package com.erolcloud.erolcloud.response;

import java.util.List;

public class AttendanceResponse {
    private final StudentResponse student;

    private final List<LectureResponse> attendances;

    public AttendanceResponse(StudentResponse student, List<LectureResponse> attendances) {
        this.student = student;
        this.attendances = attendances;
    }

    public StudentResponse getStudent() {
        return student;
    }

    public List<LectureResponse> getAttendances() {
        return attendances;
    }
}
