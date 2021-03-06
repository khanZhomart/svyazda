package com.svyazda.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import java.io.IOException;
import java.util.*;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.svyazda.dtos.ProfileInfo;
import com.svyazda.dtos.UpdateUserForm;
import com.svyazda.entities.Post;
import com.svyazda.entities.Role;
import com.svyazda.entities.User;
import com.svyazda.logging.LoggerToJson;
import com.svyazda.repositories.PostRepository;
import com.svyazda.repositories.RoleRepository;
import com.svyazda.repositories.UserRepository;
import com.svyazda.enums.Visibility;


import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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

        User targetUser = userRepository.findById(userId).orElse(null);
        Optional<User> optionalUser = userRepository.findByUsername(username);
        Collection<User> friends = targetUser.getFriends();
        Collection<User> friendRequests = targetUser.getFriendRequests();

        Collection<Post> posts = postRepository.findByAuthor(targetUser).orElse(null);

        if (optionalUser.isPresent() && !friends.contains(optionalUser.get())) {
            // filter for non-friend
            posts = posts.stream()
                    .filter(post -> post.getVisibility() != Visibility.FRIENDS)
                    .collect(Collectors.toList());
        }
        if (optionalUser.isEmpty()) {
            // filter for non-authorized
            posts = posts.stream()
                    .filter(post -> post.getVisibility() != Visibility.AUTHORIZED)
                    .collect(Collectors.toList());
        }

        ProfileInfo profileInfo = new ProfileInfo(targetUser.getUsername(), friends, posts, friendRequests);

        if (targetUser.getProfilePageVisibility() == Visibility.ALL)
            return profileInfo;
        else if (optionalUser.isPresent() &&
            targetUser.getProfilePageVisibility() == Visibility.FRIENDS &&
            targetUser.getFriends().contains(optionalUser.get())) {
            return profileInfo;
        } else if (targetUser.getProfilePageVisibility() == Visibility.AUTHORIZED && optionalUser.isPresent())
            return profileInfo;

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

    public void refreshToken(HttpServletRequest request, HttpServletResponse response, String authorizationHeader) throws IOException {

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());

                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);

                String username = decodedJWT.getSubject();
                User user = findByUsername(username).orElse(null);

                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);

                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

                Map<String, Object> map = new HashMap<>();
                map.put("type", "successfully token refreshing");
                map.put("access_token", access_token);
                map.put("time", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));

                LoggerToJson.writeToLogs(map);
            } catch (Exception e) {
                response.setHeader("Error", e.getMessage());
                response.setStatus(FORBIDDEN.value());

                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);

                Map<String, Object> map = new HashMap<>();
                map.put("type", "error token refreshing");
                map.put("access_token", null);
                map.put("time", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));

                LoggerToJson.writeToLogs(map);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }

    }
}