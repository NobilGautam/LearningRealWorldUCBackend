package com.forkcast.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.forkcast.common.constants.SecurityConstants;
import com.forkcast.common.enums.Role;

import java.util.Date;


public final class JwtUtils {
    
    private static final Algorithm algorithm = Algorithm.HMAC256(SecurityConstants.JWT_SECRET.getBytes());
    private static final JWTVerifier verifier = JWT.require(algorithm).build();
    
    private JwtUtils() {
        // Private constructor to prevent instantiation
    }
    

    public static String generateToken(String email, Role role) {
        return JWT.create()
                .withSubject(email)
                .withClaim("role", role.name())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.JWT_EXPIRATION_TIME))
                .sign(algorithm);
    }

    public static DecodedJWT verifyToken(String token) throws JWTVerificationException {
        return verifier.verify(token);
    }

    public static String getEmailFromToken(String token) {
        DecodedJWT decodedJWT = verifyToken(token);
        return decodedJWT.getSubject();
    }

    public static Role getRoleFromToken(String token) {
        DecodedJWT decodedJWT = verifyToken(token);
        String roleName = decodedJWT.getClaim("role").asString();
        return Role.valueOf(roleName);
    }

    public static boolean isTokenExpired(String token) {
        try {
            DecodedJWT decodedJWT = verifyToken(token);
            return decodedJWT.getExpiresAt().before(new Date());
        } catch (JWTVerificationException e) {
            return true;
        }
    }
}
