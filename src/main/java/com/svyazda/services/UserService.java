package com.svyazda.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;
import com.svyazda.entities.User;
import com.svyazda.repositories.UserRepository;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class UserService implements Servable<User>, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("not found"));

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });

        log.info(user.getUsername() + user.getPassword());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public List<User> findAll() {
        return Lists.newArrayList(
            this.userRepository.findAll()
        );
    }

    @Override
    public User findById(Integer id) {
        return this.userRepository.findById(id).orElse(null);
    }

    @Override
    public User save(User payload) {
        payload.setPassword(passwordEncoder.encode(payload.getPassword()));

        return this.userRepository.save(payload);
    }

    @Override
    public User update(User payload) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void remove(Integer id) {
        // TODO Auto-generated method stub
        
    }

}