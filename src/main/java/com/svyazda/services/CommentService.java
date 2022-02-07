package com.svyazda.services;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import com.svyazda.dtos.CommentForm;
import com.svyazda.entities.Comment;
import com.svyazda.entities.Post;
import com.svyazda.entities.User;
import com.svyazda.repositories.CommentRepository;

import com.svyazda.repositories.UserRepository;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostService postService;

    public List<Comment> findAll() {
        //return Lists.newArrayList(postRepository.findAll());
        return (List<Comment>) commentRepository.findAll();
    }

    public Comment findById(Long id) {
        return commentRepository.findById(id).get();
    }

    public Collection<Comment> findByPostId(Long postId) {
        Post post = postService.findById(postId);
        return commentRepository.findByPost(post).get();
    }

    public Comment save(String username, CommentForm commentForm) throws Exception {
        Comment comment = new Comment();
        Post post = postService.findById(commentForm.postId);
        if (post.isDisabledComments()) {
            throw new Exception();
        }
        comment.setAuthor(userRepository.findByUsername(username).get());
        comment.setPost(post);
        comment.setCreatedAt(new Date(new java.util.Date().getTime()));
        return commentRepository.save(comment);
    }

    public Comment update(com.svyazda.dtos.UpdateCommentForm updateCommentForm, String username) {
        User user = userRepository.findByUsername(username).get();
        Comment comment = commentRepository.findById(updateCommentForm.id).get();
        if (!comment.getAuthor().equals(user)) {
            return null;
        }

        if (updateCommentForm.text != "" && updateCommentForm.text != null) {
            comment.setText(updateCommentForm.text);
        }
        return commentRepository.save(comment);
    }

    public boolean remove(Long id, String username) {
        User user = userRepository.findByUsername(username).get();
        Comment comment = commentRepository.findById(id).get();
        if (!comment.getAuthor().equals(user)) {
            return false;
        }
        commentRepository.deleteById(id);
        return true;
    }

}