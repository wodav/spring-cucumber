package com.spring.jwt.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.spring.jwt.data.UserData;
import com.spring.jwt.data.Views;
import com.spring.jwt.response.SignInResponse;
import com.spring.jwt.services.AuthenticationService;
import com.spring.jwt.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;


@RestController
public class AuthenticationController {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    @JsonView(Views.Get.class)
    public ResponseEntity<?> registerUser(@RequestBody @JsonView(Views.Post.class) UserData userData, HttpServletRequest request) throws UnsupportedEncodingException {

        try{
            userData= userService.createUser(userData);
            return new ResponseEntity<>(userData,HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody @JsonView(Views.SignIn.class) UserData userData) {

        SignInResponse signInResponse = authenticationService.getJwtTokenAndAuthorities(userData);

        return ResponseEntity.ok(signInResponse);
    }
}
