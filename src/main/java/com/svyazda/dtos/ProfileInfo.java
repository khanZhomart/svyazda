package com.svyazda.dtos;


import com.svyazda.entities.Post;
import com.svyazda.entities.User;
import lombok.AllArgsConstructor;

import java.util.Collection;


@AllArgsConstructor
public class ProfileInfo {
    public String username;
    public Collection<User> friends;
    public Collection<Post> posts;
    public Collection<User> friendRequests;
}
