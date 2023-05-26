package com.erolcloud.erolcloud.response;

import java.util.List;

public class CourseResponse {
    private final Long id;

    private final Integer section;

    private final String code;

    private final String name;

    private final List<TimeSlotResponse> timeSlots;

    public CourseResponse(Long id, Integer section, String code, String name, List<TimeSlotResponse> timeSlots) {
        this.id = id;
        this.section = section;
        this.code = code;
        this.name = name;
        this.timeSlots = timeSlots;
    }

    public Long getId() {
        return id;
    }

    public Integer getSection() {
        return section;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public List<TimeSlotResponse> getTimeSlots() {
        return timeSlots;
    }
}
