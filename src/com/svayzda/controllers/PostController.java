package com.svayzda.controllers;

import com.svayzda.entities.Post;
import com.svayzda.repositories.PostRepository;

import java.util.Collection;


public class PostController {

    private final PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public String createPost(String title, String text, int visibility, boolean disabledComments, String author) {
        Post post = new Post(title, text, visibility, disabledComments, author);
        boolean created = postRepository.createPost(post);
        return (created ? "Post was created!" : "Post creation was failed!");
    }

    public Collection<Post> getAllPosts() {
        return postRepository.getAllPosts();
    }

//    public String getPost(int id) {
//        Post post = postRepository.getBooking(id);
//        return (post == null ? "Booking was not found!" : post.toString());
//    }
//
//    public String getAllPosts() {
//        List<Post> posts = postRepository.getAllBookings();
//        return posts.toString();
//    }
//
//    public String removePost(int id)  {
//        boolean removed = postRepository.cancelBooking(id);
//        return removed ? "Successfully removed" : "Invalid ID";
//    }

}
