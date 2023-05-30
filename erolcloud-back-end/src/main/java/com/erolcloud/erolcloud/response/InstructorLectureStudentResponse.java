package com.erolcloud.erolcloud.response;

public class InstructorLectureStudentResponse {
    private final UserResponse student;

    private final Boolean attended;

    public InstructorLectureStudentResponse(UserResponse student, Boolean attended) {
        this.student = student;
        this.attended = attended;
    }

    public UserResponse getStudent() {
        return student;
    }

    public Boolean getAttended() {
        return attended;
    }
}
