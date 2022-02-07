package com.svyazda.controllers;


import com.svyazda.dtos.CommentForm;
import com.svyazda.dtos.UpdateCommentForm;
import com.svyazda.services.CommentService;
import com.svyazda.utils.UserUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@AllArgsConstructor
@RestController
@RequestMapping("/comment-api/")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/")
    public ResponseEntity<?> save(@RequestBody CommentForm commentForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
    public ResponseEntity<?> findByPostId(@RequestParam Long postId) {
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