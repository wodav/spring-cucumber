package com.spring.jwt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="_USER") //table name "USER" is reserved
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//@GeneratedValue(strategy = GenerationType.UUID)
    private Long id;
    @Column(unique = true)
    private String username;
    @Column
    private String password;
    @Column
    private String authorities;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<SuggestionEntity> suggestions;

    public UserEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public List<SuggestionEntity> getSuggestions() {
        return this.suggestions;
    }
}
