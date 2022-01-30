package com.svyazda.repositories;

import java.util.List;
import java.util.Optional;

import com.svyazda.entities.Friend;
import com.svyazda.entities.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends CrudRepository<Friend, Integer> {
    
    boolean existsByAccepterAndSender(User user1, User user2);

    List<Friend> findAllByAccepter(User user);
    List<Friend> findAllBySender(User user);

    Optional<Friend> findByAccepter(User user);
    Optional<Friend> findBySender(User user);

    Optional<Friend> findByAccepterOrSender(User firstUser, User secondUser);
}
