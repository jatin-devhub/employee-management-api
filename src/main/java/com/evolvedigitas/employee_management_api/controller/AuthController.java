package com.evolvedigitas.employee_management_api.controller;

import com.evolvedigitas.employee_management_api.config.JwtTokenProvider;
import com.evolvedigitas.employee_management_api.exception.UnauthorizedException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        if (username.equals("admin") && password.equals("password")) {
            return jwtTokenProvider.generateToken(username);
        } else {
            throw new UnauthorizedException("Invalid username or password");
        }
    }
}
