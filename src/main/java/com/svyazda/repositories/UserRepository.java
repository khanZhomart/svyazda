package com.svyazda.repositories;

import java.util.Optional;

import com.svyazda.entities.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    
    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}