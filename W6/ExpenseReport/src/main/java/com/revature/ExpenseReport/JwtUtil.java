package com.revature.ExpenseReport;

import java.nio.charset.StandardCharsets;
import java.util.Date;

<<<<<<< HEAD
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

=======
>>>>>>> a8995cbd32d8eafeddd4c74303bbeb2c95eaec92
import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

<<<<<<< HEAD
@Component
public class JwtUtil {

    // Fields
    private static final String secret = "thisIsMySuperSecretSecuritySecret";

    private static final long expirationTime = 3600; // seconds/hour

    private static final SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
=======
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component  
public class JwtUtil {
    // Fields 
    private static final String secret = "thisIsMySuperSecretSecuritySecret";
    private static final SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    private static final long expiration = 3600; // seconds/hour
>>>>>>> a8995cbd32d8eafeddd4c74303bbeb2c95eaec92

    // Constructor

    // Methods
<<<<<<< HEAD
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime * 1000))
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
=======
    public String generateToken(String username){
        return Jwts.builder()
            .subject(username)
            .issuedAt( new Date())
            .expiration(new Date(System.currentTimeMillis() + (expiration * 1000)))
            .signWith(key)
            .compact();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token); // throws exception if invalid
>>>>>>> a8995cbd32d8eafeddd4c74303bbeb2c95eaec92
            return true;
        } catch (Exception e) {
            return false;
        }
    }

<<<<<<< HEAD
    public String getUsernameFromToken(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
    }

=======
    public String getUsernameFromToken(String token){
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
    }
>>>>>>> a8995cbd32d8eafeddd4c74303bbeb2c95eaec92
}
