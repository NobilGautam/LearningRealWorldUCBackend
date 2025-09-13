package com.forkcast.common.constants;

/**
 * Security related constants
 */
public final class SecurityConstants {
    
    private SecurityConstants() {

    }
    
    public static final String JWT_SECRET = "forkcast-jwt-secret-key-change-this-in-production";
    public static final String JWT_TOKEN_PREFIX = "Bearer ";
    public static final String JWT_HEADER_STRING = "Authorization";
    public static final String JWT_COOKIE_NAME = "jwt_token";
    public static final long JWT_EXPIRATION_TIME = 86400000L; // 24 hours in milliseconds

    public static final String AUTH_LOGIN_URL = "/api/auth/login";
    public static final String AUTH_REGISTER_URL = "/api/auth/register";
    public static final String HEALTH_CHECK_URL = "/api/health";

    public static final String[] PUBLIC_URLS = {
        AUTH_LOGIN_URL,
        AUTH_REGISTER_URL,
        HEALTH_CHECK_URL,
        "/api/docs/**",
        "/swagger-ui/**",
        "/v3/api-docs/**"
    };
}
