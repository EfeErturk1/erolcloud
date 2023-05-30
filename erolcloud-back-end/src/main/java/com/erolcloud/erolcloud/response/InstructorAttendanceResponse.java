package com.erolcloud.erolcloud.response;

import java.util.List;

public class InstructorAttendanceResponse {
    private final Long instructorId;

    private final List<InstructorLectureResponse> lectures;

    public InstructorAttendanceResponse(Long instructorId, List<InstructorLectureResponse> lectures) {
        this.instructorId = instructorId;
        this.lectures = lectures;
    }

    public Long getInstructorId() {
        return instructorId;
    }

    public List<InstructorLectureResponse> getLectures() {
        return lectures;
    }
}
