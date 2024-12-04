package com.evolvedigitas.employee_management_api.controller;

import com.evolvedigitas.employee_management_api.config.JwtTokenProvider;
import com.evolvedigitas.employee_management_api.dto.LoginRequest;
import com.evolvedigitas.employee_management_api.exception.UnauthorizedException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();
        System.out.println("username: " + username + ", password: " + password);
        if (username.equals("admin") && password.equals("password")) {
            return jwtTokenProvider.generateToken(username);
        } else {
            throw new UnauthorizedException("Invalid username or password");
        }
    }
}
