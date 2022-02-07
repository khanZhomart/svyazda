package com.svyazda.repositories;


import com.svyazda.entities.Comment;

import com.svyazda.entities.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {
    Optional<Collection<Comment>> findByPost(Post post);
}