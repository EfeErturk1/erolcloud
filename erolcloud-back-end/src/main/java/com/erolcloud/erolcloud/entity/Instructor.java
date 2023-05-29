package com.erolcloud.erolcloud.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Instructor extends User {
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "teaching",
            joinColumns = @JoinColumn(name = "instructor_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Course> teachings;

    public Instructor() {}

    public Instructor(String email, String name, String password, Set<Role> roles) {
        super(email, name, password, roles);
        teachings = new ArrayList<>();
    }

    public Instructor(String email, String name, String password, Set<Role> roles, List<Course> teachings) {
        super(email, name, password, roles);
        this.teachings = teachings;
    }

    public List<Course> getTeachings() {
        return teachings;
    }

    public void setTeachings(List<Course> teachings) {
        this.teachings = teachings;
    }
}
