package com.svyazda.services;

import com.svyazda.entities.User;
import com.svyazda.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class FriendshipService {

    private UserService userService;
    private UserRepository userRepository;

    public boolean sendFriendRequest(String sourceUserUsername, Long targetId) {
        User sourceUser = userRepository.findByUsername(sourceUserUsername).get();
        User targetUser = userService.findById(targetId);

        if (!sourceUser.getFriends().contains(targetUser) && !targetUser.getFriendRequests().contains(sourceUser)) {
            targetUser.getFriendRequests().add(sourceUser);
            userRepository.save(targetUser);
            return true;
        }

        return false;
    }

    public boolean acceptFriendRequest(Long sourceId, String targetUserUsername) {
        User targetUser = userRepository.findByUsername(targetUserUsername).get();
        User sourceUser = userService.findById(sourceId);

        if (targetUser.getFriendRequests().contains(sourceUser)) {
            targetUser.getFriendRequests().remove(sourceUser);
            sourceUser.getFriendRequests().remove(targetUser);
            targetUser.getFriends().add(sourceUser);
            sourceUser.getFriends().add(targetUser);
            userRepository.save(targetUser);
            userRepository.save(sourceUser);
            return true;
        }
        
        return false;
    }

    public boolean removeFriend(String username, Long friendId) {
        User user = userService.findByUsername(username).get();
        User friend = userRepository.findById(friendId).get();
        user.getFriends().remove(friend);
        friend.getFriends().remove(user);
        userRepository.save(user);
        userRepository.save(friend);
        return true;
    }
}
