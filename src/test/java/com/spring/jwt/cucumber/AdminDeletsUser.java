package com.spring.jwt.cucumber;

import com.spring.jwt.configurations.CucumberSpringConfiguration;
import com.spring.jwt.response.SignInResponse;
import io.cucumber.java.en.When;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

public class AdminDeletsUser extends CucumberSpringConfiguration {

    @When("Admin deletes {string}")
    public void admin_deletes(String userName) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer " + jwtToken);
        HttpEntity httpEntity = new HttpEntity<>("body",headers);

        String endpoint = "/users?userName="+userName;
        response=testRestTemplate.exchange(endpoint, HttpMethod.DELETE,httpEntity, String.class);
    }
}
