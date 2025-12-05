package com.revature.ExpenseReport.Controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.revature.ExpenseReport.Model.AppUser;
import com.revature.ExpenseReport.Repository.AppUserRepository;

import io.jsonwebtoken.security.AuthRequest;
import io.jsonwebtoken.security.AuthResponse;

@RestController
@RequestMapping("/auth")
class AuthController {
    // Fields
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // Constructors
    public AuthController(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // Methods
    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request){
        // does the user exist?
        Optional<AppUser> user = appUserRepository.findByUsername(request.getUsername());
        if(user.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        // if they do, does the password match?
        if(!passwordEncoder.matches(request.getPassword(), user.get().getPassword())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid password");
        }
        // if they do, generate a token
        String token = jwtUtil.generateToken(user.get().getUsername());
        // return the token
        return new AuthResponse(token);
    }
}