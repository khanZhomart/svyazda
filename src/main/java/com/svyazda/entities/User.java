package com.svyazda.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.sql.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.svyazda.enums.Visibility;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username = "user";
    private String password;
    private Visibility profilePageVisibility = Visibility.ALL;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date createdAt;


    @ManyToMany
    @JoinColumn(referencedColumnName = "userId")
    @JsonIgnore
    Collection<User> friendRequests = new ArrayList<>();

    @ManyToMany
    @JoinColumn(name = "friends", referencedColumnName = "userId")
    @JsonIgnore
    Collection<User> friends = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    private Collection<Role> roles = new ArrayList<>();
}

