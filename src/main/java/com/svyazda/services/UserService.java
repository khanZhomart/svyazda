package com.svyazda.services;

import java.util.List;

import com.google.common.collect.Lists;
import com.svyazda.entities.User;
import com.svyazda.repositories.UserRepository;
import com.svyazda.utils.exceptions.UserAlreadyExistException;
import com.svyazda.utils.exceptions.UserDoesNotExistException;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class UserService implements Servable<User> {
    private final UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return Lists.newArrayList(
            this.userRepository.findAll()
        );
    }

    @Override
    public User findById(Integer id) {
        return this.userRepository.findById(id).orElse(null);
    }

    @Override
    public User save(User payload) {

//        if (this.userRepository.existsByEmail(payload.getEmail()))
//            throw new UserAlreadyExistException("This email already registered");

        return this.userRepository.save(payload);
    }

    @Override
    public User update(User payload) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void remove(Integer id) {
        // TODO Auto-generated method stub
        
    }

}