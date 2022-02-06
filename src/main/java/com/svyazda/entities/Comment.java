package com.svyazda.entities;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "comments")
@AllArgsConstructor
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;


    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date createdAt;
    private String text;

    @OneToOne
    @JoinColumn(name = "author", referencedColumnName = "userId")
    private User author;

    private int likes;
}