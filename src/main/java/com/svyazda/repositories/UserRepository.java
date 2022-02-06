package com.svyazda.repositories;

import com.svyazda.entities.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    boolean existsByEmail(String email);
    
    boolean existsByUsername(String username);
}