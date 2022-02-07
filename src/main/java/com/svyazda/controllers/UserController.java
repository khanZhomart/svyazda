package com.svyazda.controllers;

import com.svyazda.dtos.UpdateUserForm;
import com.svyazda.entities.User;
import com.svyazda.services.UserService;
import com.svyazda.utils.Converter;
import com.svyazda.utils.UserUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



@AllArgsConstructor
@RestController
@RequestMapping("/user-api/")
class UserController {

    private final UserService userService;

    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody User payload) throws NumberFormatException {
        User user = userService.save(payload);
        userService.addRoleToUser(user.getUsername(), "ROLE_USER");
        return ResponseEntity.ok(user);
    }

    @GetMapping("/")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(this.userService.findAll());
    }

    @Deprecated
    @GetMapping("/user")
    public ResponseEntity<?> findById(@RequestParam Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @DeleteMapping("/")
    public ResponseEntity<?> remove(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = UserUtil.getUsernameByToken(request, response);
        userService.remove(username);
        return ResponseEntity.ok("removed");
    }

    @PutMapping("/")
    public ResponseEntity<?> update(@RequestBody UpdateUserForm updateForm, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return ResponseEntity.ok(userService.update(updateForm, UserUtil.getUsernameByToken(request, response)));
    }

    @GetMapping("/profile")
    public ResponseEntity<?> profileInfo(@RequestParam Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return ResponseEntity.ok(Converter.ObjectToJson(userService.getProfileInfo(id, UserUtil.getUsernameByToken(request, response))));
    }

}