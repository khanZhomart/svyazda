package com.svyazda.services;

import java.util.List;

import com.google.common.collect.Lists;
import com.svyazda.entities.Post;
import com.svyazda.repositories.PostRepository;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class PostService implements CrudService<Post> {
    private final PostRepository postRepository;

    @Override
    public List<Post> findAll() {
        return Lists.newArrayList(
            this.postRepository.findAll()
        );
    }

    @Override
    public Post findById(String id) {
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
    public void remove(String id) {
        // TODO Auto-generated method stub
        
    }

}