package com.forkcast.app.controllers;

import com.forkcast.common.constants.SecurityConstants;
import com.forkcast.dao.dto.AuthResponse;
import com.forkcast.dao.dto.LoginRequest;
import com.forkcast.dao.dto.RegisterRequest;
import com.forkcast.service.AuthService;
import com.forkcast.service.impl.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Component
@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthController {

    @Autowired
    private AuthService authService;

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @POST
    @Path("/login")
    public Response login(LoginRequest loginRequest, @Context HttpServletResponse response) {
        log.info("Login request received for email: {}", loginRequest.getEmail());
        
        try {
            AuthResponse authResponse = authService.login(loginRequest);

            setJwtCookie(response, authResponse.getToken());
            
            log.info("Login successful for email: {}", loginRequest.getEmail());
            
            return Response.ok(authResponse).build();
        } catch (Exception e) {
            log.error("Login failed for email: {}", loginRequest.getEmail(), e);
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"message\":\"" + e.getMessage() + "\"}")
                    .build();
        }
    }
    
    @POST
    @Path("/register")
    public Response register(RegisterRequest registerRequest, @Context HttpServletResponse response) {
        log.info("Register request received for email: {}", registerRequest.getEmail());
        
        try {
            AuthResponse authResponse = authService.register(registerRequest);

            setJwtCookie(response, authResponse.getToken());
            
            log.info("Registration successful for email: {}", registerRequest.getEmail());
            
            return Response.ok(authResponse).build();
        } catch (Exception e) {
            log.error("Registration failed for email: {}", registerRequest.getEmail(), e);
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\":\"" + e.getMessage() + "\"}")
                    .build();
        }
    }
    
    @POST
    @Path("/logout")
    public Response logout(@Context HttpServletResponse response) {
        log.info("Logout request received");

        clearJwtCookie(response);
        
        log.info("Logout successful");
        
        return Response.ok("{\"message\":\"Logout successful\"}").build();
    }
    
    private void setJwtCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie(SecurityConstants.JWT_COOKIE_NAME, token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // Set to true in production with HTTPS
        cookie.setPath("/");
        cookie.setMaxAge((int) (SecurityConstants.JWT_EXPIRATION_TIME / 1000)); // Convert to seconds
        response.addCookie(cookie);
    }
    
    private void clearJwtCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(SecurityConstants.JWT_COOKIE_NAME, "");
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // Set to true in production with HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
