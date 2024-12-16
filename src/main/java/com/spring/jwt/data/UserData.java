package com.spring.jwt.data;

import com.fasterxml.jackson.annotation.JsonView;

public class UserData {
    @JsonView(value=Views.Get.class)
    private long id;
    @JsonView(value={Views.SignIn.class,Views.Get.class, Views.Post.class})
    private String userName;
    @JsonView(value={Views.SignIn.class, Views.Post.class, Views.Patch.class})
    private String password;
    @JsonView(value={Views.SignIn.class, Views.Get.class, Views.PostForAdmin.class, Views.PatchForAdmin.class})
    private String authorities;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
