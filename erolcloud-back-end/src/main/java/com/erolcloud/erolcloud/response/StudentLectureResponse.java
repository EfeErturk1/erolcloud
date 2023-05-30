package com.erolcloud.erolcloud.response;

public class StudentLectureResponse {
    private final LectureResponse lecture;

    private final Boolean attended;

    public StudentLectureResponse(LectureResponse lectureResponse, Boolean attended) {
        this.lecture = lectureResponse;
        this.attended = attended;
    }

    public LectureResponse getLecture() {
        return lecture;
    }

    public Boolean getAttended() {
        return attended;
    }
}
