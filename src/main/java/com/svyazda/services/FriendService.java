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

    public void save(User user1, Integer id) throws UserDoesNotExistException {
        User user2 = this.userRepository.findById(id).orElseThrow(() -> new UserDoesNotExistException(id));

        if (user1.getUserId() > user2.getUserId()) {
            User temp = user1;
            user1 = user2;
            user2 = temp;
        }

        if (this.friendRepository.existsByFirstUserAndSecondUser(user1, user2))
            return;

        Friend friend = new Friend();
        friend.setCreatedDate(new Date());
        friend.setFirstUser(user1);
        friend.setSecondUser(user2);

        this.friendRepository.save(friend);
    }

    public List<Friend> findAllByUserId(Integer id) throws UserDoesNotExistException {
        User user = this.userRepository.findById(id).orElseThrow(() -> new UserDoesNotExistException(id));

        return this.friendRepository.findAllByFirstUser(user);
    }
    /*
    public void acceptFriend(Integer accepterId) throws UserDoesNotExistException {
        User accepter = this.userRepository.findById(accepterId).orElseThrow(() -> new UserDoesNotExistException(accepterId));

        Friend friend = this.friendRepository.findByFirstUserOrSecondUser(accepter).orElseThrow(() -> new UserDoesNotExistException(accepterId));

        friend.setAccepted(true);
        this.friendRepository.save(friend);
    }

    public void declineFriend(Integer declinerId) throws UserDoesNotExistException {
        User decliner = this.userRepository.findById(declinerId).orElseThrow(() -> new UserDoesNotExistException(declinerId));

        Friend friend = this.friendRepository.findByFirstUserOrSecondUser(decliner).orElseThrow(() -> new UserDoesNotExistException(declinerId));

        this.friendRepository.delete(friend);
    }
    */
}
