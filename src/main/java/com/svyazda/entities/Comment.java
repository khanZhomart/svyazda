package com.svyazda.entities;

import javax.persistence.*;

import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Entity
@Table(name = "comments")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    private String text;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "author", referencedColumnName = "userId")
    private User author;

    @ManyToOne
    @JoinColumn(name = "post", referencedColumnName = "postId")
    private Post post;
}
