package com.svyazda.repositories;

import java.util.List;
import java.util.Optional;

import com.svyazda.entities.Friend;
import com.svyazda.entities.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends CrudRepository<Friend, Integer> {
    
    boolean existsByFirstUserAndSecondUser(User user1, User user2);

    List<Friend> findAllByFirstUser(User user);
    List<Friend> findAllBySecondUser(User user);

    Optional<Friend> findByFirstUser(User user);
    Optional<Friend> findBySecondUser(User user);

    Optional<Friend> findByFirstUserOrSecondUser(User user1, User user2);
}
