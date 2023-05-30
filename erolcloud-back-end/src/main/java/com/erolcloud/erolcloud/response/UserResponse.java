package com.erolcloud.erolcloud.response;

public class UserResponse {
    private final Long id;

    private final String email;

    private final String name;

    public UserResponse(Long id, String email, String name) {
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
