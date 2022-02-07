package com.svyazda.controllers;


import com.svyazda.services.FriendshipService;
import com.svyazda.utils.UserUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@AllArgsConstructor
@RestController
@RequestMapping("/friendship-api/")
public class FriendshipController {

    private final FriendshipService friendService;

    @PostMapping("/friend-request")
    public ResponseEntity<?> friendRequest(@RequestParam Long targetId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return ResponseEntity.ok(friendService.sendFriendRequest(UserUtil.getUsernameByToken(request, response), targetId));
    }

    @PostMapping("/friend-accept")
    public ResponseEntity<?> friendAccept(@RequestParam Long sourceId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return ResponseEntity.ok(friendService.acceptFriendRequest(sourceId, UserUtil.getUsernameByToken(request, response)));
    }

    @PostMapping("/remove")
    public ResponseEntity<?> remove(@RequestParam Long friendId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return ResponseEntity.ok(friendService.removeFriend(UserUtil.getUsernameByToken(request, response), friendId));
    }
}
