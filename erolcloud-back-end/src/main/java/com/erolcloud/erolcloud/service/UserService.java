package com.erolcloud.erolcloud.service;

import com.erolcloud.erolcloud.entity.Role;
import com.erolcloud.erolcloud.entity.Student;
import com.erolcloud.erolcloud.entity.User;
import com.erolcloud.erolcloud.exception.EntityAlreadyExistsException;
import com.erolcloud.erolcloud.exception.EntityNotFoundException;
import com.erolcloud.erolcloud.repository.RoleRepository;
import com.erolcloud.erolcloud.repository.StudentRepository;
import com.erolcloud.erolcloud.repository.UserRepository;
import com.erolcloud.erolcloud.request.LoginRequest;
import com.erolcloud.erolcloud.request.RegisterRequest;
import com.erolcloud.erolcloud.response.LoginResponse;
import com.erolcloud.erolcloud.response.RegisterResponse;
import com.erolcloud.erolcloud.security.JwtUtils;
import com.erolcloud.erolcloud.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RoleRepository roleRepository;

    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return new LoginResponse(token, userDetails.getId(), userDetails.getEmail(), userDetails.getName(),
                userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()));
    }

    public RegisterResponse registerUser(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new EntityAlreadyExistsException("User with email " + registerRequest.getEmail() + " already exists.");
        }
        User user = studentRepository.save(new Student(registerRequest.getEmail(), registerRequest.getName(),
                passwordEncoder.encode(registerRequest.getPassword()),
                Collections.singleton(roleRepository.findByAuthority(Role.Authority.STUDENT)
                        .orElseThrow(() -> new EntityNotFoundException("Authority " + Role.Authority.STUDENT + " does not exist.")))));
        return new RegisterResponse(user.getId(), user.getEmail(), user.getName(),
                user.getRoles().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()));
    }
}
