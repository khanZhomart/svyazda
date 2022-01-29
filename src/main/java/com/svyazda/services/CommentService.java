package com.svyazda.services;

import java.util.List;

import com.google.common.collect.Lists;
import com.svyazda.entities.Comment;
import com.svyazda.repositories.CommentRepository;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class CommentService implements CrudService<Comment> {
    private final CommentRepository commentRepository;

    @Override
    public List<Comment> findAll() {
        return Lists.newArrayList(
            this.commentRepository.findAll()
        );
    }

    @Override
    public Comment findById(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Comment save(Comment payload) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Comment update(Comment payload) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void remove(String id) {
        // TODO Auto-generated method stub
        
    }

}