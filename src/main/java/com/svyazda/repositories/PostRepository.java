package com.svyazda.repositories;


import com.svyazda.entities.Post;

import com.svyazda.entities.User;
import com.svyazda.enums.Visibility;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
    Optional<Collection<Post>> findByAuthor(User user);
    Optional<Collection<Post>> findAllByVisibility(Visibility visibility);
}