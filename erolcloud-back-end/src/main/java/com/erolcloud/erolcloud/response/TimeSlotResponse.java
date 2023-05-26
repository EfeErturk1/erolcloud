package com.erolcloud.erolcloud.response;

import java.time.LocalTime;

public class TimeSlotResponse {
    private final Long id;

    private final String day;

    private final LocalTime startTime;

    private final LocalTime endTime;

    public TimeSlotResponse(Long id, String day, LocalTime startTime, LocalTime endTime) {
        this.id = id;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public String getDay() {
        return day;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
}
