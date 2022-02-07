package com.svyazda.entities;

import javax.persistence.*;

import com.svyazda.enums.Visibility;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Table(name = "posts")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;
    private String title;
    private String text;
    private boolean disabledComments = false;
    private Visibility visibility = Visibility.ALL;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "author", referencedColumnName = "userId")
    private User author;

}
