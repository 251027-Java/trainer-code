package com.revature.ExpenseReport.Controller;

import com.revature.ExpenseReport.Model.AppUser;
import com.revature.ExpenseReport.Repository.AppUserRepository;

import com.revature.ExpenseReport.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    public record AuthRequest(String username, String password) {}
    public record AuthResponse(String token) {}

    // Fields
    AppUserRepository appUserRepository;
    PasswordEncoder passwordEncoder;
    JwtUtil jwtUtil;

    // Constructors
    public AuthController(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // Methods
    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        Optional<AppUser> user = appUserRepository.findByUsername(request.username());

        if(user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
        }

        if(!passwordEncoder.matches(request.password(), user.get().getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid " +
                    "password");
        }

        String token = jwtUtil.generateToken(user.get().getUsername());
        return new AuthResponse(token);
    }
}
