package com.erolcloud.erolcloud.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Student extends User {
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "enrollment",
            joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Course> courses;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "attendance",
            joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "lecture_id"))
    private List<Lecture> attendances;

    public Student() {}

    public Student(String email, String name, String password, Set<Role> roles) {
        super(email, name, password, roles);
        courses = new ArrayList<>();
        attendances = new ArrayList<>();
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public List<Lecture> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<Lecture> attendances) {
        this.attendances = attendances;
    }
}
