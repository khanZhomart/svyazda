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

//    List<Friend> findAllByAccepterAndTypeOrSenderAndType(User accepter, String type, User sender, String type2);
    List<Friend> findAllByAccepterAndAcceptedOrSenderAndAccepted(User accepter, boolean accepted, User sender, boolean accepted1);
    List<Friend> findAllByAccepterAndAccepted(User accepter, boolean accepted);

    Optional<Friend> findByAccepter(User user);
    Optional<Friend> findBySender(User user);

    void deleteByAccepterAndSender(User accepter, User sender);

    Optional<Friend> findByAccepterAndSender(User firstUser, User secondUser);
}
