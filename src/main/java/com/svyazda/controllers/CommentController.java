package com.svyazda.controllers;


import com.svyazda.dtos.CommentForm;
import com.svyazda.dtos.UpdateCommentForm;
import com.svyazda.logging.LoggerToJson;
import com.svyazda.services.CommentService;
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
@RequestMapping("/comment-api/")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody CommentForm commentForm, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String, Object> map = new HashMap<>();
        map.put("name", "commenting");
        map.put("type", "REQUEST (POST)");
        map.put("comment", commentForm);
        map.put("time", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));

        LoggerToJson.writeToLogs(map);

        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", "commenting");
        map1.put("type", "RESPONSE");
        map1.put("info", commentService.save(UserUtil.getUsernameByToken(request, response), commentForm));
        map1.put("time", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));

        LoggerToJson.writeToLogs(map1);

        return ResponseEntity.ok(commentService.save(UserUtil.getUsernameByToken(request, response), commentForm));
    }

    @Deprecated
    @GetMapping("/")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(commentService.findAll());
    }

    @GetMapping("/comment")
    public ResponseEntity<?> findById(@RequestParam Long id) {
        return ResponseEntity.ok(commentService.findById(id));
    }

    @GetMapping("/post-comments")
    public ResponseEntity<?> findByPostId(@RequestParam Long postId) throws IOException {

        Map<String, Object> map = new HashMap<>();
        map.put("name", "getting all coments");
        map.put("postId", postId);
        map.put("type", "REQUEST (GET)");
        map.put("time", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));

        LoggerToJson.writeToLogs(map);

        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", "getting all coments");
        map1.put("type", "RESPONSE");
        map.put("postId", postId);
        map1.put("info", commentService.findByPostId(postId).size());
        map1.put("time", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));

        LoggerToJson.writeToLogs(map1);

        return ResponseEntity.ok(commentService.findByPostId(postId));
    }

    @DeleteMapping("/")
    public ResponseEntity<?> remove(@RequestParam Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return ResponseEntity.ok(commentService.remove(id, UserUtil.getUsernameByToken(request, response)));
    }

    @PutMapping("/")
    public ResponseEntity<?> update(@RequestBody UpdateCommentForm updateForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return ResponseEntity.ok(commentService.update(updateForm, UserUtil.getUsernameByToken(request, response)));
    }

}