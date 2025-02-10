package com.example.dosomething.controller;

import com.example.dosomething.dto.MessageResponse;
import com.example.dosomething.dto.UserDTO;
import com.example.dosomething.model.User;
import com.example.dosomething.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<MessageResponse> login(@RequestBody UserDTO loginRequest) {
        User user = userService.findByEmail(loginRequest.getEmail());
        if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.ok(new MessageResponse("Login successful"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Invalid email or password"));
        }
    }

    // Register endpoint
    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(@RequestBody UserDTO userDTO) {
        if (userService.findByEmail(userDTO.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("User already exists"));
        }
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setName(userDTO.getName());
        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("User registered successfully"));
    }

    // Reset password endpoint
    @PostMapping("/reset-password")
    public ResponseEntity<MessageResponse> resetPassword(@RequestBody UserDTO request) {
        User user = userService.findByEmail(request.getEmail());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("User not found"));
        }
        // Simple password reset logic
        String newPassword = "newpassword"; // Replace with a secure password generation
        user.setPassword(passwordEncoder.encode(newPassword));
        userService.saveUser(user);
        return ResponseEntity.ok(new MessageResponse("Password reset successful. Check your email for the new password."));
    }
}
