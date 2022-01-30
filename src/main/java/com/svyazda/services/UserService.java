package com.svyazda.services;

import java.util.List;

import com.google.common.collect.Lists;
import com.svyazda.entities.User;
import com.svyazda.repositories.UserRepository;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
class UserService implements Servable<User> {
    private final UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return Lists.newArrayList(
            this.userRepository.findAll()
        );
    }

    @Override
    public User findById(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User save(User payload) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User update(User payload) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void remove(String id) {
        // TODO Auto-generated method stub
        
    }

}