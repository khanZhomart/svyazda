package com.svyazda.repositories;

import java.util.List;

import com.svyazda.entities.Friend;
import com.svyazda.entities.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends CrudRepository<Friend, Integer> {
    
    boolean existsByFirstUserAndSecondUser(User user1, User user2);

    List<Friend> findByFirstUser(User user);
    List<Friend> findBySecondUser(User user);
}
