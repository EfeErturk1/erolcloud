package com.erolcloud.erolcloud.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RegisterRequest {
    @NotBlank
    @Size(min = 2)
    private String name;

    @NotBlank
    @Email
    @Size(max = 50)
    private String email;

    @NotBlank
    @Size(min = 6)
    private String password;

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
