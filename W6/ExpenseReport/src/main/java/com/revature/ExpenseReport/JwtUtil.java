package com.revature.ExpenseReport;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {
    // Fields
    private static final String secret = "thisismysupersecretSecuritySecret";

    private static final SecretKey key =
            Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

    private static final long expiration = 3600 * 1000; // 1 hr as seconds

    // Constructor

    // Methods
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().verifyWith(key)
                .build().parseSignedClaims(token)
                .getPayload().getSubject();
    }
}
