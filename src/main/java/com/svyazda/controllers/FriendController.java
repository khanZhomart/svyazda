package com.svyazda.controllers;

import com.svyazda.entities.User;
import com.svyazda.services.FriendService;
import com.svyazda.services.UserService;
import com.svyazda.utils.exceptions.UserDoesNotExistException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/friend")
@AllArgsConstructor
public class FriendController {
    private final UserService userService;
    private final FriendService friendService; 

    @PostMapping("/")
    public ResponseEntity<?> saveFriend(@RequestParam String friendId, @RequestParam String senderId) throws NumberFormatException, UserDoesNotExistException {
        User user = this.userService.findById(Integer.parseInt(senderId));
        this.friendService.save(user, Integer.parseInt(friendId));
        return ResponseEntity.ok().body("created");
    }

    // TODO: исправить логическую ошибку метода acceptFriend в friendService
    @PostMapping("/accept/")
    public ResponseEntity<?> acceptFriend(@RequestParam String friendId, @RequestParam String userId) {

        return null;
    }
}
