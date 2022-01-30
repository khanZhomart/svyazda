package com.svyazda.controllers;

import com.svyazda.entities.User;
import com.svyazda.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        try {
            this.userService.save(user);
            return ResponseEntity.ok().body("created");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Registration error");
        }
    }
    
}