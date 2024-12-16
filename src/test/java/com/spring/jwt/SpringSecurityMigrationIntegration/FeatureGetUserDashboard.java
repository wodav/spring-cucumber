package com.spring.jwt.SpringSecurityMigrationIntegration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.jwt.configurations.JUnitSpringConfiguration;
import com.spring.jwt.data.UserData;
import com.spring.jwt.mapper.FileUtil;

import com.spring.jwt.response.SignInResponse;
import org.junit.jupiter.api.*;

import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


/*
public class FeatureGetUserDashboard extends JUnitSpringConfiguration {

    @Test
    @Order(1)
    public void givenSignUpRequest_shouldSuccedWith200() throws Exception{

        String signUpRequestJson = FileUtil.readFromFileToString("/requests/sign_up_request.json");

        UserData signUpRequest = new ObjectMapper().readValue(signUpRequestJson, UserData.class);

        ResponseEntity<String> response = template.postForEntity("/signup", signUpRequest, String.class);
        UserData signUpResponse = mapper.convertValue(response.getBody(), UserData.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John", signUpResponse.getUserName().replace("\"",""));
    }

    @Test
    @Order(2)
    public void givenSignUpRequestUserAlreadyExists_shouldFailWithBadRequest400() throws Exception{
        String signUpRequestJson = FileUtil.readFromFileToString("/requests/sign_up_request.json");
        UserData signUpRequest = new ObjectMapper().readValue(signUpRequestJson, UserData.class);
        ResponseEntity<String> result = template.postForEntity("/signup", signUpRequest, String.class);
        assertEquals(HttpStatus.CONFLICT, result.getStatusCode());
        assertEquals("Error: Username is already taken!", result.getBody());
    }

    @Test
    @Order(3)
    public void givenSignInRequest_shouldSuccedWith200() throws Exception{
        String signInRequestJson = FileUtil.readFromFileToString("/requests/sign_in_request.json");
        UserData signInRequest = new ObjectMapper().readValue(signInRequestJson, UserData.class);
        ResponseEntity<SignInResponse> jwtResponse = template.postForEntity("/signin", signInRequest, SignInResponse.class);
        assertEquals(HttpStatus.OK, jwtResponse.getStatusCode());
        assertEquals("[ROLE_USER, CONFIRMED]", jwtResponse.getBody().getAuthorities());
    }

    @Test
    @Order(4)
    public void givenRestCallWithBearerTokenGetDashboard_shouldSuccedWith200()throws Exception{
        String signInRequestJson = FileUtil.readFromFileToString("/requests/sign_in_request.json");
        UserData signInRequest = new ObjectMapper().readValue(signInRequestJson, UserData.class);
        ResponseEntity<SignInResponse> jwtResponse = template.postForEntity("/signin", signInRequest, SignInResponse.class);
        assertEquals(HttpStatus.OK, jwtResponse.getStatusCode());
        assertEquals("[ROLE_USER, CONFIRMED]", jwtResponse.getBody().getAuthorities());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+ jwtResponse.getBody().getAccessToken());
        HttpEntity httpEntity = new HttpEntity<>("body",headers);

        ResponseEntity<String> stringResponse=template.exchange("/users/1/dashboard", HttpMethod.GET,httpEntity, String.class);
        assertEquals(HttpStatus.OK,stringResponse.getStatusCode());
        assertEquals("My Dashboard",stringResponse.getBody());
    }

    @Test
    @Order(5)
    public void givenRestCallWithBearerTokenGetUser_shouldFailWith403()throws Exception{

        String signInRequestJson = FileUtil.readFromFileToString("/requests/sign_in_request.json");
        UserData signInRequest = new ObjectMapper().readValue(signInRequestJson, UserData.class);
        ResponseEntity<SignInResponse> jwtResponse = template.postForEntity("/signin", signInRequest, SignInResponse.class);
        assertEquals(HttpStatus.OK, jwtResponse.getStatusCode());
        assertEquals("[ROLE_USER, CONFIRMED]", jwtResponse.getBody().getAuthorities());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+ jwtResponse.getBody().getAccessToken());
        HttpEntity httpEntity = new HttpEntity<>("body",headers);

        ResponseEntity<String> stringResponse2=template.exchange("/users/", HttpMethod.GET,httpEntity, String.class);
        assertEquals(HttpStatus.UNAUTHORIZED,stringResponse2.getStatusCode());
    }
}*/
