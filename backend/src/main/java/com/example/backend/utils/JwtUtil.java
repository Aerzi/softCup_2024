package com.example.backend.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.backend.config.property.SystemConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.Calendar;
import java.util.Date;

@Component
public class JwtUtil {
    private static SystemConfig systemConfig;
    @Autowired
    public void setSystemConfig(SystemConfig systemConfig){
        JwtUtil.systemConfig = systemConfig;
    }

    public static String generateToken(String username) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE,systemConfig.getJwtConfig().getExpiration()); // 默认过期时间 7天

        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC512(systemConfig.getJwtConfig().getSecretKey()));
    }

    public static String getUsernameFromToken(String token) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(systemConfig.getJwtConfig().getSecretKey()))
                .build()
                .verify(token);
        return decodedJWT.getSubject();
    }

    public static boolean validateToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC512(systemConfig.getJwtConfig().getSecretKey()))
                    .build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException exception) {
            return false;
        }
    }
}
