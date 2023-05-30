package com.erolcloud.erolcloud.response;

import java.util.List;

public class InstructorLectureResponse {
    private final LectureResponse lecture;

    private final List<InstructorLectureStudentResponse> studentAttendances;

    public InstructorLectureResponse(LectureResponse lectureResponse, List<InstructorLectureStudentResponse> studentAttendances) {
        this.lecture = lectureResponse;
        this.studentAttendances = studentAttendances;
    }

    public LectureResponse getLecture() {
        return lecture;
    }

    public List<InstructorLectureStudentResponse> getStudentAttendances() {
        return studentAttendances;
    }
}
