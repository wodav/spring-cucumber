package com.spring.jwt.data;

import com.fasterxml.jackson.annotation.JsonView;

public class SuggestionData {
    @JsonView(value=Views.Get.class)
    private Long id;
    @JsonView(value={Views.Get.class, Views.Post.class, Views.PatchForAdmin.class})
    private String text;
    @JsonView(value={Views.GetForAdmin.class})
    private UserData user;
    @JsonView(value={Views.GetForAdmin.class, Views.PatchForAdmin.class})
    private Boolean isValidated=false;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserData getUser() {
        return user;
    }

    public void setUser(UserData user) {
        this.user = user;
    }

    public Boolean getValidated() {
        return isValidated;
    }

    public void setValidated(Boolean validated) {
        isValidated = validated;
    }
}
