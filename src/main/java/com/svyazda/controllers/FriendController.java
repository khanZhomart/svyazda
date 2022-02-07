package com.svyazda.controllers;

import com.svyazda.entities.User;
import com.svyazda.services.FriendService;
import com.svyazda.services.UserService;
import com.svyazda.utils.exceptions.UserDoesNotExistException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok().body("Friendship requested");
    }

    @GetMapping("/")
    public ResponseEntity<?> getFriends(@RequestParam String userId, @RequestParam String friendType) throws Exception {

        return ResponseEntity.ok(this.friendService.findAllFriends(Integer.parseInt(userId), friendType));
    }

    @PostMapping("/accept/")
    public ResponseEntity<?> acceptFriend(@RequestParam String accepterId, @RequestParam String senderId) throws UserDoesNotExistException {

        this.friendService.acceptFriend(Integer.parseInt(accepterId), Integer.parseInt(senderId));

        return ResponseEntity.ok("Friend request accepted");
    }

    @PostMapping("/decline/")
    public ResponseEntity<?> declineFriend(@RequestParam Integer declinerId, @RequestParam Integer senderId) throws UserDoesNotExistException {
        return ResponseEntity.ok("Friend request declined");
    }

}
