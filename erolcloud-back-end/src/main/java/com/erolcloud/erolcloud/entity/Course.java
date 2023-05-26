package com.erolcloud.erolcloud.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer section;

    private String code;

    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(joinColumns = @JoinColumn(name = "course_id"), inverseJoinColumns = @JoinColumn(name = "time_slot_id"))
    private List<TimeSlot> timeSlots;

    public Course() {}

    public Course(Integer section, String code, String name, List<TimeSlot> timeSlots) {
        this.section = section;
        this.code = code;
        this.name = name;
        this.timeSlots = timeSlots;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSection() {
        return section;
    }

    public void setSection(Integer section) {
        this.section = section;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(List<TimeSlot> timeSlots) {
        this.timeSlots = timeSlots;
    }
}
