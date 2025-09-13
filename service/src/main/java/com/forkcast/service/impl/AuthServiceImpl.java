package com.forkcast.service.impl;

import com.forkcast.common.exceptions.AuthenticationException;
import com.forkcast.common.utils.JwtUtils;
import com.forkcast.dao.dto.AuthResponse;
import com.forkcast.dao.dto.LoginRequest;
import com.forkcast.dao.dto.RegisterRequest;
import com.forkcast.dao.entities.User;
import com.forkcast.dao.repository.UserRepository;
import com.forkcast.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    
    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        log.info("Attempting login for user: {}", loginRequest.getEmail());
        
        Optional<User> userOpt = userRepository.findByEmail(loginRequest.getEmail());
        if (userOpt.isEmpty()) {
            throw new AuthenticationException("Invalid email or password");
        }
        
        User user = userOpt.get();
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new AuthenticationException("Invalid email or password");
        }
        
        String token = JwtUtils.generateToken(user.getEmail(), user.getRole());
        
        log.info("Login successful for user: {}", loginRequest.getEmail());
        
        return AuthResponse.builder()
                .token(token)
                .email(user.getEmail())
                .role(user.getRole())
                .message("Login successful")
                .build();
    }
    
    @Override
    public AuthResponse register(RegisterRequest registerRequest) {
        log.info("Attempting registration for user: {}", registerRequest.getEmail());
        
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new AuthenticationException("Email already exists");
        }
        
        User user = User.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .role(registerRequest.getRole())
                .build();
        
        userRepository.save(user);
        
        String token = JwtUtils.generateToken(user.getEmail(), user.getRole());
        
        log.info("Registration successful for user: {}", registerRequest.getEmail());
        
        return AuthResponse.builder()
                .token(token)
                .email(user.getEmail())
                .role(user.getRole())
                .message("Registration successful")
                .build();
    }
}
