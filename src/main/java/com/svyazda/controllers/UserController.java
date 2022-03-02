package com.svyazda.controllers;

import com.svyazda.dtos.UpdateUserForm;
import com.svyazda.entities.User;
import com.svyazda.logging.LoggerToJson;
import com.svyazda.services.UserService;
import com.svyazda.utils.Converter;
import com.svyazda.utils.UserUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;


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
    public ResponseEntity<?> findAll() throws IOException {

        Map<String, Object> map = new HashMap<>();
        map.put("name", "getting all users");
        map.put("type", "REQUEST (GET)");
        map.put("time", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));

        LoggerToJson.writeToLogs(map);

        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", "getting all users");
        map1.put("type", "RESPONSE");
        map1.put("info", this.userService.findAll().size());
        map1.put("time", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));

        LoggerToJson.writeToLogs(map1);

        return ResponseEntity.ok(this.userService.findAll());
    }

    @Deprecated
    @GetMapping("/user")
    public ResponseEntity<?> findById(@RequestParam Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @DeleteMapping("/")
    public ResponseEntity<?> remove(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return ResponseEntity.ok(userService.remove(UserUtil.getUsernameByToken(request, response)));
    }

    @PutMapping("/")
    public ResponseEntity<?> update(@RequestBody UpdateUserForm updateForm, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return ResponseEntity.ok(userService.update(updateForm, UserUtil.getUsernameByToken(request, response)));
    }

    @GetMapping("/profile")
    public ResponseEntity<?> profileInfo(@RequestParam Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {

        Map<String, Object> map = new HashMap<>();
        map.put("name", "getting profile data");
        map.put("type", "REQUEST (GET)");
        map.put("id", id);
        map.put("time", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));

        LoggerToJson.writeToLogs(map);

        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", "getting profile data");
        map1.put("type", "RESPONSE");
        map1.put("info", ResponseEntity.ok(Converter.ObjectToJson(userService.getProfileInfo(id, UserUtil.getUsernameByToken(request, response)))));
        map1.put("time", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));

        LoggerToJson.writeToLogs(map1);

        return ResponseEntity.ok(Converter.ObjectToJson(userService.getProfileInfo(id, UserUtil.getUsernameByToken(request, response))));
    }

    @PostMapping("/token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);

        userService.refreshToken(request, response, authorizationHeader);
    }

}