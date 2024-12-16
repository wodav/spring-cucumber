package com.spring.jwt.response;

public class SignInResponse {
    private String token;
    private String type = "Bearer";

    private String userName;
    private String authorities;

    public SignInResponse(){

    }
    public SignInResponse(String accessToken, String username, String authorities) {
        this.token = accessToken;
        this.userName = username;
        this.authorities = authorities;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }
}