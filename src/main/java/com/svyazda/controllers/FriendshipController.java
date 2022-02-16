package com.svyazda.controllers;


import com.svyazda.logging.LoggerToJson;
import com.svyazda.services.FriendshipService;
import com.svyazda.utils.UserUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@AllArgsConstructor
@RestController
@RequestMapping("/friendship-api/")
public class FriendshipController {

    private final FriendshipService friendService;

    @PostMapping("/friend-request")
    public ResponseEntity<?> friendRequest(@RequestParam Long targetId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "sending friend request");
        map.put("type", "POST");
        map.put("accepter",  targetId);
        map.put("sender", UserUtil.getUsernameByToken(request, response));
        map.put("time", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));

        LoggerToJson.writeToLogs(map);

        return ResponseEntity.ok(friendService.sendFriendRequest(UserUtil.getUsernameByToken(request, response), targetId));
    }

    @PostMapping("/friend-accept")
    public ResponseEntity<?> friendAccept(@RequestParam Long sourceId, HttpServletRequest request, HttpServletResponse response) throws IOException {

        Map<String, Object> map = new HashMap<>();
        map.put("name", "accepting friend request");
        map.put("type", "POST");
        map.put("sender",  sourceId);
        map.put("accepter", UserUtil.getUsernameByToken(request, response));
        map.put("time", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));

        LoggerToJson.writeToLogs(map);

        return ResponseEntity.ok(friendService.acceptFriendRequest(sourceId, UserUtil.getUsernameByToken(request, response)));
    }

    @PostMapping("/remove")
    public ResponseEntity<?> remove(@RequestParam Long friendId, HttpServletRequest request, HttpServletResponse response) throws IOException {

        Map<String, Object> map = new HashMap<>();
        map.put("name", "declining friend request or deleting friend");
        map.put("type", "POST");
        map.put("friendId",  friendId);
        map.put("username", UserUtil.getUsernameByToken(request, response));
        map.put("time", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));

        LoggerToJson.writeToLogs(map);

        return ResponseEntity.ok(friendService.removeFriend(UserUtil.getUsernameByToken(request, response), friendId));
    }
}
