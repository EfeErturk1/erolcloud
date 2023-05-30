package com.erolcloud.erolcloud.response;

public class InstructorLectureStudentResponse {
    private final StudentResponse student;

    private final Boolean attended;

    public InstructorLectureStudentResponse(StudentResponse student, Boolean attended) {
        this.student = student;
        this.attended = attended;
    }

    public StudentResponse getStudent() {
        return student;
    }

    public Boolean getAttended() {
        return attended;
    }
}
