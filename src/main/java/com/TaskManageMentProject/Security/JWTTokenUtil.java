package com.TaskManageMentProject.Security;

import java.security.Key;
import java.util.Date;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Component;

import com.TaskManageMentProject.Entity.UserAuthenticate;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTTokenUtil {

    private final Key key;
    private final long TOKEN_VALIDITY = 1000L * 60 * 60 * 24; 

    public JWTTokenUtil() {
        String secret = System.getenv("JWT_SECRET");

        if (secret == null || secret.isBlank()) {
            
            secret = "ReplaceThisWithAVeryVerySecretKeyOfAtLeast32Bytes!";
        }

        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

   
    public String generateToken(UserAuthenticate user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + TOKEN_VALIDITY);

        return Jwts.builder()
                .setSubject(user.getUserOfficialEmail())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Validate JWT token
    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    // Extract email from token
    public String extractUserEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
