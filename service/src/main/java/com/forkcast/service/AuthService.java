package com.forkcast.service;

import com.forkcast.dao.dto.AuthResponse;
import com.forkcast.dao.dto.LoginRequest;
import com.forkcast.dao.dto.RegisterRequest;


public interface AuthService {

    AuthResponse login(LoginRequest loginRequest);

    AuthResponse register(RegisterRequest registerRequest);
}
