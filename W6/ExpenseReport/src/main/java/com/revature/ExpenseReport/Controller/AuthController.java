package com.revature.ExpenseReport.Controller;

import java.util.Optional;

<<<<<<< HEAD
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.revature.ExpenseReport.Model.AppUser;
import com.revature.ExpenseReport.Repository.AppUserRepository;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;

import com.revature.ExpenseReport.JwtUtil;

@RestController
@RequestMapping("/auth")
class AuthController {
=======
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.revature.ExpenseReport.JwtUtil;
import com.revature.ExpenseReport.Model.AppUser;
import com.revature.ExpenseReport.Repository.AppUserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

// Controller Class
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    // Auth Records
    public record AuthRequest(String username, String password){}
    public record AuthResponse(String token){}

>>>>>>> a8995cbd32d8eafeddd4c74303bbeb2c95eaec92

    // Fields
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

<<<<<<< HEAD
    // Constructor
=======
    // Constructors
>>>>>>> a8995cbd32d8eafeddd4c74303bbeb2c95eaec92
    public AuthController(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // Methods
    @PostMapping("/login")
<<<<<<< HEAD
    public AuthResponse login(@RequestBody AuthRequest request) {
        // does the user exist?
        Optional<AppUser> user = appUserRepository.findByUsername(request.getUsername());
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        // if they do, check the password
        if (!passwordEncoder.matches(request.getPassword(), user.get().getPassword())) {
=======
    public AuthResponse login(@RequestBody AuthRequest request){
        // does the user exist?
        Optional<AppUser> user = appUserRepository.findByUsername(request.username());
        if(user.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        // if they do, does the password match?
        if(!passwordEncoder.matches(request.password(), user.get().getPassword())){
>>>>>>> a8995cbd32d8eafeddd4c74303bbeb2c95eaec92
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");
        }
        // if they do, generate a token
        String token = jwtUtil.generateToken(user.get().getUsername());
<<<<<<< HEAD
        // if they do not, throw an exception
        return new AuthResponse(token);
    }

}

class AuthRequest {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

class AuthResponse {
    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
=======
        // return the token
        return new AuthResponse(token);
    }
}
>>>>>>> a8995cbd32d8eafeddd4c74303bbeb2c95eaec92
