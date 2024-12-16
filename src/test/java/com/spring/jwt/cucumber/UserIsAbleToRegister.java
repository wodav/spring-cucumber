package com.spring.jwt.cucumber;

import com.spring.jwt.configurations.CucumberSpringConfiguration;
import com.spring.jwt.data.UserData;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class UserIsAbleToRegister extends CucumberSpringConfiguration {

    @When("{string} signs up with {string}")
    public void clientSignsUp(String userName, String password) throws IOException {

        JSONObject userData = new JSONObject();
        userData.put("userName",userName);
        userData.put("password",password);

        HttpEntity httpEntity = new HttpEntity<>(userData);
        response = testRestTemplate.exchange("/register",HttpMethod.POST, httpEntity, UserData.class);
    }

    @Then("{string} receives username")
    public void receives_username(String userName) {

        UserData signUpResponse = mapper.convertValue(response.getBody(), UserData.class);
        assertEquals(userName, signUpResponse.getUserName().replace("\"",""));
    }

    @Then("he receives {string}")
    public void he_receives(String statusCode) {
        assertEquals(statusCode, response.getStatusCode());
    }

    @Given("{string} is signed up with {string}")
    public void is_signed_up_with(String userName, String password) throws IOException {

        clientSignsUp(userName,password);
        receives_username(userName);
    }
}
