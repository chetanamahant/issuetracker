package com.example.issuetracker.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.Map;


@Component
public class JwtUtil {

    // 🔐 SECRET & EXPIRY INSIDE CODE
    private static final String SECRET_KEY =
            "chetanamahant1234567890chetanamahant1234567890chetanamahant1234567890";

    private static final long EXPIRATION_TIME = 86400000; // 24 hours

    // ✅ DEFINE KEY ONCE
    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());


    // ✅ Generate Token
    public String generateToken(String username, String role) {

        return Jwts.builder()
                .setClaims(Map.of("role", role))
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // ✅ EXTRACT USERNAME (SAFE METHOD)
    public String extractUsername(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }


    // ✅ VALIDATE TOKEN (EXPIRY + SIGNATURE)
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
