package com.svyazda.controllers;


import com.svyazda.dtos.UpdatePostForm;
import com.svyazda.entities.Post;
import com.svyazda.logging.LoggerToJson;
import com.svyazda.services.PostService;
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
@RequestMapping("/post-api/")
public class PostController {

    private final PostService postService;

    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody Post post, HttpServletRequest request, HttpServletResponse response) throws IOException {

        Map<String, Object> map = new HashMap<>();
        map.put("name", "creating post");
        map.put("type", "REQUEST");
        map.put("author",  post.getAuthor());
        map.put("title", post.getTitle());
        map.put("text", post.getText());
        map.put("time", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));

        LoggerToJson.writeToLogs(map);

        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", "creating post");
        map1.put("type", "RESPONSE");
        map1.put("body", postService.save(post, UserUtil.getUsernameByToken(request, response)));
        map.put("time", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));

        LoggerToJson.writeToLogs(map1);

        return ResponseEntity.ok(postService.save(post, UserUtil.getUsernameByToken(request, response)));
    }

    @Deprecated
    @GetMapping("/all")
    public ResponseEntity<?> findAll() throws IOException {

        return ResponseEntity.ok(postService.findAll());
    }

    @Deprecated
    @GetMapping("/visibilityAll")
    public ResponseEntity<?> findAllWithVisibilityAll() {
        return ResponseEntity.ok(postService.findAllWithVisibilityAll());
    }

    @GetMapping("/")
    public ResponseEntity<?> findAllConsideringVisibility(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Map<String, Object> map = new HashMap<>();
        map.put("name", "getting all posts");
        map.put("type", "REQUEST (GET)");
        map.put("time", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));

        LoggerToJson.writeToLogs(map);

        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", "getting all posts");
        map1.put("type", "RESPONSE");
        map1.put("info", postService.findAll().size());
        map1.put("time", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));

        LoggerToJson.writeToLogs(map1);

        return ResponseEntity.ok(postService.findAllWithVisibilityAuthorizedOrFriend(UserUtil.getUsernameByToken(request, response)));
    }


    @GetMapping("/post")
    public ResponseEntity<?> findById(@RequestParam Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return ResponseEntity.ok(postService.findById(id, UserUtil.getUsernameByToken(request, response)));
    }

    @DeleteMapping("/")
    public ResponseEntity<?> remove(@RequestParam Long id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return ResponseEntity.ok(postService.remove(id, UserUtil.getUsernameByToken(request, response)));
    }

    @PutMapping("/")
    public ResponseEntity<?> update(@RequestBody UpdatePostForm updateForm, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return ResponseEntity.ok(postService.update(updateForm, UserUtil.getUsernameByToken(request, response)));
    }

    @PutMapping("/disable-comment")
    public ResponseEntity<?> disableCommentOnPost(@RequestParam Long postId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return ResponseEntity.ok(postService.disableComment(postId, UserUtil.getUsernameByToken(request, response)));
    }

    @PutMapping("/enable-comment")
    public ResponseEntity<?> enableCommentOnPost(@RequestParam Long postId, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return ResponseEntity.ok(postService.enableComment(postId, UserUtil.getUsernameByToken(request, response)));
    }
}