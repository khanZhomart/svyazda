package com.svyazda.utils;

import com.svyazda.entities.User;

public class UserUtil {
    
    public static User mergeInstances(User user1, User user2) {
        return User.builder()
            .userId(user1.getUserId())
            .username(user2.getUsername() == null ? user1.getUsername() : user2.getUsername())
            .password(user2.getPassword() == null ? user1.getPassword() : user2.getPassword())
            .build();
    }

    public static boolean validCredentials(String username, String password) {
        return (username == null || username.isEmpty()) || (password == null || password.isEmpty());
    }
}
