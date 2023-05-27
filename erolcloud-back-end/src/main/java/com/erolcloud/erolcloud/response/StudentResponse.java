package com.erolcloud.erolcloud.response;

public class StudentResponse {
    private final Long id;

    private final String email;

    private final String name;

    public StudentResponse(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}
