package com.evolvedigitas.employee_management_api.controller;

import com.evolvedigitas.employee_management_api.config.JWT.JwtAuthenticationFilter;
import com.evolvedigitas.employee_management_api.config.JWT.JwtTokenProvider;
import com.evolvedigitas.employee_management_api.dto.LoginRequest;
import com.evolvedigitas.employee_management_api.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthService authService;

    public AuthController(JwtTokenProvider jwtTokenProvider, JwtAuthenticationFilter jwtAuthenticationFilter, AuthService authService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtAuthenticationFilter= jwtAuthenticationFilter;
        this.authService= authService;

    }

    @GetMapping("/verify-token")
    public ResponseEntity<?> verifyToken(HttpServletRequest request){
        String token= jwtAuthenticationFilter.getJwtFromRequest(request);
        Map<String, Object> customResponse= new HashMap<>();
        if(token!=null) {
            if(jwtTokenProvider.validateToken(token)) {
                customResponse.put("message","Token is Valid");
                return new ResponseEntity<>(customResponse, HttpStatus.OK);
            } else {
                customResponse.put("message","Unauthorized: Invalid or missing token");
                return new ResponseEntity<>(customResponse, HttpStatus.UNAUTHORIZED);
            }
        }
        customResponse.put("message","Server Error");
        return new ResponseEntity<>(customResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        boolean isValid= authService.verifyUser(loginRequest);
        Map<String, Object> customResponse= new HashMap<>();
        if (isValid) {
            String token= jwtTokenProvider.generateToken(loginRequest.getUsername());
            customResponse.put("token", token);
            customResponse.put("message", "Login successful");
            return new ResponseEntity<>(customResponse, HttpStatus.OK);
        } else {
            customResponse.put("message", "Invalid username or password");
            return new ResponseEntity<>(customResponse, HttpStatus.UNAUTHORIZED);
        }
    }
}
