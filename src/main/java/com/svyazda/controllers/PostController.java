package com.svyazda.controllers;


import com.svyazda.dtos.UpdatePostForm;
import com.svyazda.entities.Post;
import com.svyazda.services.PostService;
import com.svyazda.utils.UserUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;


@AllArgsConstructor
@RestController
@RequestMapping("/post-api/")
public class PostController {

    // ws-related
    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public Collection<Post> receiveMessage(@Payload Post post){
        postService.save(post, "rakha");
        return postService.findAll();
    }

    private final PostService postService;

    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody Post post, HttpServletRequest request, HttpServletResponse response) throws IOException {
        return ResponseEntity.ok(postService.save(post, UserUtil.getUsernameByToken(request, response)));
    }

    @Deprecated
    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(postService.findAll());
    }

    @Deprecated
    @GetMapping("/visibilityAll")
    public ResponseEntity<?> findAllWithVisibilityAll() {
        return ResponseEntity.ok(postService.findAllWithVisibilityAll());
    }

    @GetMapping("/")
    public ResponseEntity<?> findAllConsideringVisibility(HttpServletRequest request, HttpServletResponse response) throws IOException {
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