package com.spring.jwt.cucumber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spring.jwt.configurations.CucumberSpringConfiguration;
import com.spring.jwt.response.SignInResponse;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class UserIsAbleToSignIn extends CucumberSpringConfiguration {

    @When("{string} signs in with {string}")
    public void userSignsIn(String userName, String password) throws IOException {

        JSONObject userData = new JSONObject();
        userData.put("userName",userName);
        userData.put("password",password);

        HttpEntity httpEntity = new HttpEntity<>(userData);
        ResponseEntity<SignInResponse> signInResponse = testRestTemplate.exchange("/login", HttpMethod.POST, httpEntity, SignInResponse.class);
        response = signInResponse;
        jwtToken = signInResponse.getBody().getAccessToken();
    }

    @Then("Signin succeeds with {string}")
    public void signInSucceeds(String authorities) throws JsonProcessingException {
        SignInResponse signInResponse = mapper.convertValue(response.getBody(),SignInResponse.class);
        assertEquals(authorities, signInResponse.getAuthorities());
    }

    @Then("Signin fails")
    public void signInFails(){

      //  SignInResponse signInResponse = mapper.convertValue(response.getBody(),SignInResponse.class);
        //assertEquals("[ROLE_USER, CONFIRMED]", signInResponse.getAuthorities());
    }

    @Given("{string} is signed in with {string}")
    public void userSignedInWith(String userName, String password) throws IOException {

        userSignsIn(userName,password);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
