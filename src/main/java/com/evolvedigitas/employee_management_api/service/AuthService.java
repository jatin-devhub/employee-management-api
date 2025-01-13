package com.evolvedigitas.employee_management_api.service;

import com.evolvedigitas.employee_management_api.dto.LoginRequest;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public boolean verifyUser(LoginRequest loginRequest) {
        if (loginRequest.getUsername().equals("admin") && loginRequest.getPassword().equals("password")) {
            return true;
        }
        return false;
    }

}
