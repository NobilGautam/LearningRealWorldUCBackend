package com.forkcast.app.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.forkcast.common.constants.SecurityConstants;
import com.forkcast.common.enums.Role;
import com.forkcast.common.utils.JwtUtils;
import com.forkcast.service.impl.AuthServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

/**
 * JWT Authentication Filter
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        
        String token = extractToken(request);

        //Todo: check if the email extracted from token exists in the db or not
        if (token != null) {
            try {
                String email = JwtUtils.getEmailFromToken(token);
                Role role = JwtUtils.getRoleFromToken(token);
                
                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    Collection<GrantedAuthority> authorities = Collections.singletonList(
                            new SimpleGrantedAuthority(role.getAuthority())
                    );
                    
                    UsernamePasswordAuthenticationToken authToken = 
                            new UsernamePasswordAuthenticationToken(email, null, authorities);
                    
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    
                    log.debug("Successfully authenticated user: {} with role: {}", email, role);
                }
            } catch (JWTVerificationException e) {
                log.warn("Invalid JWT token: {}", e.getMessage());
                clearTokenCookie(response);
            }
        }
        
        filterChain.doFilter(request, response);
    }
    
    private String extractToken(HttpServletRequest request) {
        // First, try to get token from Authorization header
        String bearerToken = request.getHeader(SecurityConstants.JWT_HEADER_STRING);
        if (bearerToken != null && bearerToken.startsWith(SecurityConstants.JWT_TOKEN_PREFIX)) {
            return bearerToken.substring(SecurityConstants.JWT_TOKEN_PREFIX.length());
        }
        
        // If not found in header, try to get from cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (SecurityConstants.JWT_COOKIE_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        
        return null;
    }
    
    private void clearTokenCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(SecurityConstants.JWT_COOKIE_NAME, "");
        cookie.setHttpOnly(true);
        cookie.setSecure(false); // Set to true in production with HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
