package com.erolcloud.erolcloud.response;

import java.util.Set;

public class RegisterResponse {
    private final Long id;

    private final String email;

    private final String name;

    private final Set<String> roles;

    public RegisterResponse(Long id, String email, String name, Set<String> roles) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.roles = roles;
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

    public Set<String> getRoles() {
        return roles;
    }
}
