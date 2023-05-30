package com.erolcloud.erolcloud.response;

import java.util.List;

public class StudentCourseAttendanceResponse {
    private final Long studentId;

    private final Long courseId;

    private final List<StudentLectureResponse> lectures;

    public StudentCourseAttendanceResponse(Long studentId, Long courseId, List<StudentLectureResponse> lectures) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.lectures = lectures;
    }

    public Long getStudentId() {
        return studentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public List<StudentLectureResponse> getLectures() {
        return lectures;
    }
}
