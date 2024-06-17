package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.extern.java.Log;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity

public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private String location;
    private Integer likes;

    @Column
    @ElementCollection(targetClass = String.class)
    private Set<String> likedUsers = new HashSet<String>();
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER , mappedBy = "post",orphanRemoval = true)
    private List<Comment> comments = new ArrayList<Comment>();
    @Column(updatable = false)
    private LocalDateTime createdDate;



    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
    }

}
