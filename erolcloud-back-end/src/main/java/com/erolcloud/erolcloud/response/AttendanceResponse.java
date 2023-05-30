package com.erolcloud.erolcloud.response;

import java.util.List;

public class AttendanceResponse {
    private final UserResponse student;

    private final List<LectureResponse> attendances;

    public AttendanceResponse(UserResponse student, List<LectureResponse> attendances) {
        this.student = student;
        this.attendances = attendances;
    }

    public UserResponse getStudent() {
        return student;
    }

    public List<LectureResponse> getAttendances() {
        return attendances;
    }
}
