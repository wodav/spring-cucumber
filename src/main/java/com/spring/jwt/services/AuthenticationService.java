package com.spring.jwt.services;

import com.spring.jwt.data.UserData;
import com.spring.jwt.response.SignInResponse;
import com.spring.jwt.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;

    public String getJwtToken(String userName, String password){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return jwtUtils.generateJwtToken(authentication);
    }

    public SignInResponse getJwtTokenAndAuthorities(UserData userData){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userData.getUserName(), userData.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new SignInResponse(jwtUtils.generateJwtToken(authentication),userDetails.getUsername(), userDetails.getAuthorities().toString());
    }
}
