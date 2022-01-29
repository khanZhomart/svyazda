package com.svyazda.repositories;

import com.svyazda.entities.Post;

import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, String> {

}