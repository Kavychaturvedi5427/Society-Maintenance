package com.kavya.societymaintenance.service;


import com.kavya.societymaintenance.dto.Request.LoginRequest;
import com.kavya.societymaintenance.dto.Request.RegisterRequest;
import com.kavya.societymaintenance.dto.Response.AuthResponse;


public interface AuthService {
    
    AuthResponse register(RegisterRequest registerRequest);

    AuthResponse login(LoginRequest loginRequest);
}
