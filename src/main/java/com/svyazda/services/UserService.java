package com.svyazda.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;
import com.svyazda.dtos.ProfileInfo;
import com.svyazda.dtos.UpdateUserForm;
import com.svyazda.entities.Post;
import com.svyazda.entities.Role;
import com.svyazda.entities.User;
import com.svyazda.repositories.PostRepository;
import com.svyazda.repositories.RoleRepository;
import com.svyazda.repositories.UserRepository;
import com.svyazda.enums.Visibility;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return Lists.newArrayList(
                this.userRepository.findAll()
        );
    }

    public User findById(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public ProfileInfo getProfileInfo(Long userId, String username) {
        if (userId == 0) {
            User targetUser = userRepository.findByUsername(username).get();
            Collection<Post> posts = postRepository.findByAuthor(targetUser).get();
            ProfileInfo profileInfo = new ProfileInfo(targetUser.getUsername(), targetUser.getFriends(), posts, targetUser.getFriendRequests());
            return profileInfo;
        }

        User targetUser = userRepository.findById(userId).get();
        Optional<User> optionalUser = userRepository.findByUsername(username);

        Collection<Post> posts = postRepository.findByAuthor(targetUser).get();
        Collection<User> friends = targetUser.getFriends();
        Collection<User> friendRequests = targetUser.getFriendRequests();
        ProfileInfo profileInfo = new ProfileInfo(targetUser.getUsername(), friends, posts, friendRequests);

        if (targetUser.getProfilePageVisibility() == Visibility.ALL) {
            return profileInfo;
        }

        else if (targetUser.getProfilePageVisibility() == Visibility.FRIENDS &&
            targetUser.getFriends().contains(optionalUser.get())) {
            return profileInfo;
        } else if (targetUser.getProfilePageVisibility() == Visibility.AUTHORIZED && optionalUser.isPresent()) {
            return profileInfo;
        }
        return null;
    }

    public User save(User payload) {
        payload.setPassword(passwordEncoder.encode(payload.getPassword()));
        payload.setCreatedAt(new java.sql.Date(new java.util.Date().getTime()));
        return userRepository.save(payload);
    }

    public User update(User payload) {
        User user = userRepository.findById(payload.getUserId()).get();

        if (payload.getUsername() != "") 
            user.setUsername(payload.getUsername());
        
        if (payload.getPassword() != "") 
            user.setPassword(passwordEncoder.encode(payload.getPassword()));
        
        return this.userRepository.save(user);
    }

    public User update(UpdateUserForm payload, String username) {
        User user = userRepository.findByUsername(username).get();

        if (payload.username != "" && payload.username != null)
            user.setUsername(payload.username);
        
        if (payload.profilePageVisibility != null)
            user.setProfilePageVisibility(payload.profilePageVisibility);
        
        if (payload.password != "" && payload.password != null)
                user.setPassword(passwordEncoder.encode(payload.password));
        
        return this.userRepository.save(user);
    }

    public boolean remove(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isPresent()) {
            userRepository.deleteById(optionalUser.get().getUserId());
            return true;
        }

        return false;
    }

    ///////// Role
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Transactional
    public void addRoleToUser(String username, String roleName) {
        User user = userRepository.findByUsername(username).get();
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    public void removeRole(String roleName) {
        roleRepository.deleteByName(roleName);
    }
    //////////

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty())
            throw new UsernameNotFoundException("not found");

        User user = userOptional.get();
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}