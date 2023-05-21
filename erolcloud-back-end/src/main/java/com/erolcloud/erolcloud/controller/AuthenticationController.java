package com.erolcloud.erolcloud.controller;

import com.erolcloud.erolcloud.request.LoginRequest;
import com.erolcloud.erolcloud.response.LoginResponse;
import com.erolcloud.erolcloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(userService.authenticateUser(loginRequest), HttpStatus.OK);
    }
}
