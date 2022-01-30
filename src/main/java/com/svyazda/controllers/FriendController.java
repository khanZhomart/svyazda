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
    public ResponseEntity<?> saveFriend(@RequestParam String accepterId, @RequestParam String senderId) throws NumberFormatException, UserDoesNotExistException {
        User accepter = this.userService.findById(Integer.parseInt(accepterId));
        this.friendService.save(accepter, Integer.parseInt(senderId));
        return ResponseEntity.ok().body("created");
    }

    // TODO: исправить логическую ошибку метода acceptFriend в friendService
    @PostMapping("/accept/")
    public ResponseEntity<?> acceptFriend(@RequestParam String accepterId/*, @RequestParam String userId*/) throws UserDoesNotExistException {

        return ResponseEntity.ok(this.friendService.findAllByUserId(Integer.parseInt(accepterId)));
    }
}
