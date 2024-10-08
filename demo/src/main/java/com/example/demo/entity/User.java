package com.example.demo.entity;

import com.example.demo.entity.enums.ERole;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Data
@Entity
public class User implements UserDetails {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String name;

        @Column(unique = true, updatable = true)
        private String username;

        @Column(nullable = false)
        private String lastname;

        @Column(length = 3000)
        private String password;

        @Column(unique = true)
        private String email;

        @Column(columnDefinition = "text")
        private String bio;

        @ElementCollection(targetClass = ERole.class)
        @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
        @Enumerated(EnumType.STRING)
        private Set<ERole> role = new HashSet<>();

        @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
        private List<Post> posts = new ArrayList<>();

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @Column(updatable = false)
        private LocalDateTime createdDate;

        @Transient
        private Collection<? extends GrantedAuthority> authorities;
        public User() {

        }

        public User(Long id,
                    String username,
                    String email,
                    String password,
                    Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        }

        @PrePersist
        protected void onCreate() {
            this.createdDate = LocalDateTime.now();
        }
        /*
            SECURITY
         */
        @Override
        public String getPassword(){
            return password;
        }

        public Set<ERole> getRoles(){
                return role;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }
        @Override
        public boolean isAccountNonLocked() {
            return true;
        }
        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }
        @Override
        public boolean isEnabled() {
            return true;
        }


}
