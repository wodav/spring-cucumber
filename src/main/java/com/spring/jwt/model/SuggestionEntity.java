package com.spring.jwt.model;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;

@Entity(name="suggestion")
public class SuggestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//@GeneratedValue(strategy = GenerationType.UUID)
    private Long id;
    @JsonValue
    @Column(unique = true)
    private String text;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
    @Column
    private Boolean isValidated=false;

    public SuggestionEntity() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public Boolean getValidated() {
        return isValidated;
    }

    public void setValidated(Boolean validated) {
        this.isValidated = validated;
    }
}
