package com.erolcloud.erolcloud.response;

import java.util.Set;

public class LoginResponse {
    private final String token;

    private final String type = "Bearer";

    private final Long id;

    private final String email;

    private final String name;

    private final Set<String> roles;

    public LoginResponse(String token, Long id, String email, String name, Set<String> roles) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.name = name;
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
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
