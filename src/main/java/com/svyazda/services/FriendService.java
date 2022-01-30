package com.svyazda.services;

import java.util.Date;
import java.util.List;

import com.svyazda.dto.UserDTO;
import com.svyazda.dto.converters.UserConverter;
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
}
