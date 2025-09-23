package com.example.gympool.service;

import com.example.gympool.dto.LoginRequest;
import com.example.gympool.dto.LoginResponse;
import com.example.gympool.dto.RegisterRequest;

public interface AuthService {
    LoginResponse login(LoginRequest request);
    void register(RegisterRequest request);
    void registerEmployee(RegisterRequest request);
}
