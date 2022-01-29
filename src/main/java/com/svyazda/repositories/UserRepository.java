package com.svyazda.repositories;

import com.svyazda.entities.User;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
    
}