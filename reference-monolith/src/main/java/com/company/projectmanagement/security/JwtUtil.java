package com.company.projectmanagement.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // Secret key (must be at least 256 bits for HS256)
    //Sign and verify the token
    private static final String SECRET = "mysecretkeymysecretkeymysecretkey123";

    // Token validity (example: 10 hours)
    private static final long JWT_EXPIRATION = 1000 * 60 * 60 * 10;

    // Generate signing key
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // Generate token
    public String generateToken(String username) {
        //eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9... FINAL
        return Jwts.builder()
                .setSubject(username)                 // store username
                .setIssuedAt(new Date())              // current time
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Extract username from token
    public String extractUsername(String token) {

        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    // Validate token
    public boolean validateToken(String token, String username) {

        String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    // Check token expiry
    private boolean isTokenExpired(String token) {

        Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    // Extract all claims
    private Claims extractAllClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}