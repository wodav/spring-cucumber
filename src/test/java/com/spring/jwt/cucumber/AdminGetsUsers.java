package com.spring.jwt.cucumber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.jwt.configurations.CucumberSpringConfiguration;
import com.spring.jwt.data.UserData;
import com.spring.jwt.model.UserEntity;
import io.cucumber.java.en.Then;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AdminGetsUsers extends CucumberSpringConfiguration {

    @Then("receives list of users")
    public void receives_list_of_users() throws JsonProcessingException {
        ObjectMapper objectMapper= new ObjectMapper();
        String responseBody = (String) response.getBody();
        assertThat(objectMapper.readValue(responseBody, new TypeReference<List<UserData>>(){}))
                .isInstanceOf(List.class)
                .hasOnlyElementsOfType(UserData.class);
    }

    @Then("receives one user")
    public void receives_one_user() throws JsonProcessingException {
        ObjectMapper objectMapper= new ObjectMapper();
        String responseBody = (String) response.getBody();
        assertThat(objectMapper.readValue(responseBody, UserData.class))
                .isInstanceOf(UserData.class);
    }
}
