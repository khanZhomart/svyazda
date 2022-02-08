package com.svyazda.services;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.svyazda.dtos.UpdatePostForm;
import com.svyazda.entities.Post;
import com.svyazda.entities.User;
import com.svyazda.repositories.PostRepository;

import com.svyazda.repositories.UserRepository;
import com.svyazda.enums.Visibility;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public Collection<Post> findAll() {
        //return Lists.newArrayList(postRepository.findAll());
        return (Collection<Post>) postRepository.findAll();
    }

    public Collection<Post> findAllWithVisibilityAll() {
        return postRepository.findAllByVisibility(Visibility.ALL).get();
    }

    public Collection<Post> findAllWithVisibilityAuthorizedOrFriend(String username) {
        Collection<Post> allPosts = postRepository.findAllByVisibility(Visibility.ALL).get();
        if (username == "") {
            return allPosts;
        }
        Collection<Post> authPosts = postRepository.findAllByVisibility(Visibility.AUTHORIZED).get();
        Stream<Post> combinedStream = Stream.concat(allPosts.stream(), authPosts.stream());

        User user = userRepository.findByUsername(username).get();

        Collection<User> friends = user.getFriends();
        for (User friend: friends) {
            Collection<Post> friendPosts = postRepository.findByAuthor(friend).get();
            friendPosts = friendPosts.stream().filter(
                    friendPost -> friendPost.getVisibility() == Visibility.FRIENDS).collect(Collectors.toList());
            combinedStream = Stream.concat(combinedStream,  friendPosts.stream());
        }
        Collection<Post> myPostsForFriends = postRepository.findByAuthor(user).get();
        myPostsForFriends = myPostsForFriends.stream().filter(
                post -> post.getVisibility() == Visibility.FRIENDS).collect(Collectors.toList());
        combinedStream = Stream.concat(combinedStream, myPostsForFriends.stream());
        return combinedStream.collect(Collectors.toList());
    }

    public Post findById(Long id) {
        return postRepository.findById(id).get();
    }

    public Post findById(Long id, String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        Post post = postRepository.findById(id).get();

        if (post.getVisibility() == Visibility.ALL) {
            return post;
        }

        if (optionalUser.isPresent()) {
            if (post.getAuthor().equals(optionalUser.get())) {
                return post;
            }
            if (post.getVisibility() == Visibility.FRIENDS && !optionalUser.get().getFriends().contains(post.getAuthor())) {
                return null;
            }
            return post;
        }
        return null;
    }

    public Post save(Post payload, String username) {
        payload.setAuthor(userRepository.findByUsername(username).get());
        payload.setCreatedAt(new Date(new java.util.Date().getTime()));
        return postRepository.save(payload);
    }

    public Post update(UpdatePostForm payload, String username) {
        Post post = postRepository.findById(payload.id).get();
        if (!post.getAuthor().equals(userRepository.findByUsername(username).get())) {
            return null;
        }

        if (payload.title != "" && payload.title != null) {
            post.setTitle(payload.title);
        }
        if (payload.text != "" && payload.text != null) {
            post.setText(payload.text);
        }
        if (payload.disabledComments != post.isDisabledComments()) {
            post.setDisabledComments(!post.isDisabledComments());
        }
        if (payload.visibility != null) {
            post.setVisibility(payload.visibility);
        }
        return postRepository.save(post);
    }

    public boolean remove(Long postId, String username) {
        User user = userRepository.findByUsername(username).get();
        Post post = postRepository.findById(postId).get();
        if (post.getAuthor().equals(user)) {
            postRepository.deleteById(postId);
            return true;
        }
        return false;
    }

    public boolean disableComment(Long postId, String username) {
        User user = userRepository.findByUsername(username).get();
        Post post = postRepository.findById(postId).get();
        if (post.getAuthor().equals(user)) {
            post.setDisabledComments(true);
            postRepository.save(post);
            return true;
        }
        return false;
    }

    public boolean enableComment(Long postId, String username) {
        User user = userRepository.findByUsername(username).get();
        Post post = postRepository.findById(postId).get();
        if (post.getAuthor().equals(user)) {
            post.setDisabledComments(false);
            postRepository.save(post);
            return true;
        }
        return false;
    }

}