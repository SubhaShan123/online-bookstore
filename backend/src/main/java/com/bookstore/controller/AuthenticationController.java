package com.bookstore.controller;

import com.bookstore.model.User;
import com.bookstore.dto.AuthRequest;
import com.bookstore.dto.AuthResponse;
import com.bookstore.exception.ResourceNotFoundException;
import com.bookstore.service.UserService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticationController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody AuthRequest authRequest){
        User user = new User(null, authRequest.getUsername(), authRequest.getPassword());
        User registeredUser = userService.registerUser(user);

        AuthResponse response = new AuthResponse(registeredUser.getUsername(),"User registered successfully");

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest authRequest){
        try{
            User user = userService.getUserByUsername(authRequest.getUsername());

            if(!passwordEncoder.matches(authRequest.getPassword(),user.getPassword())){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
            }
            AuthResponse response = new AuthResponse(user.getUsername(),"User Authentication successful");
            return ResponseEntity.ok(response);
        }
        catch(ResourceNotFoundException Ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");

        }
    }
}
