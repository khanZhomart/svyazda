package com.svyazda.services;

import java.util.List;

import com.google.common.collect.Lists;
import com.svyazda.entities.Post;
import com.svyazda.repositories.PostRepository;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostService implements Servable<Post> {
    private final PostRepository postRepository;

    @Override
    public List<Post> findAll() {
        return Lists.newArrayList(
            this.postRepository.findAll()
        );
    }

    @Override
    public Post findById(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Post save(Post payload) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Post update(Post payload) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void remove(Integer id) {
        // TODO Auto-generated method stub
        
    }

}