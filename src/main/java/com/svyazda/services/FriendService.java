package com.svyazda.services;

import java.util.Date;
import java.util.List;

import com.svyazda.entities.Friend;
import com.svyazda.entities.User;
import com.svyazda.repositories.FriendRepository;
import com.svyazda.repositories.UserRepository;
import com.svyazda.utils.exceptions.UserDoesNotExistException;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class FriendService {
    private FriendRepository friendRepository;
    private UserRepository userRepository;

    public void save(User accepter, Integer senderId) throws UserDoesNotExistException {
        User sender = this.userRepository.findById(senderId).orElseThrow(() -> new UserDoesNotExistException(senderId));

        if (this.friendRepository.existsByAccepterAndSender(accepter, sender))
            return;

        Friend friend = new Friend();
        friend.setCreatedDate(new Date());
        friend.setAccepter(accepter);
        friend.setSender(sender);

        this.friendRepository.save(friend);
    }

    public List<Friend> findAllByUserId(Integer id) throws UserDoesNotExistException {
        User user = this.userRepository.findById(id).orElseThrow(() -> new UserDoesNotExistException(id));

        return this.friendRepository.findAllByAccepter(user);
    }

    public List<Friend> findAllFriends(Integer id, String type) throws Exception {
        User user = this.userRepository.findById(id).orElseThrow(() -> new UserDoesNotExistException(id));

        if (type.equals("friendRequest")) {
            return this.friendRepository.findAllByAccepterAndAccepted(user, false);
        }
        if (type.equals("friend")) {
            return this.friendRepository.findAllByAccepterAndAcceptedOrSenderAndAccepted(user, true, user, true);
        }

        throw new Exception("Incorrect type!");
    }

    public void acceptFriend(Integer accepterId, Integer senderId) throws UserDoesNotExistException {

    }
}
