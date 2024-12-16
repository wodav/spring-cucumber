package com.spring.jwt.cucumber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.spring.jwt.configurations.CucumberSpringConfiguration;
import com.spring.jwt.data.SuggestionData;
import com.spring.jwt.data.UserData;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserGetsSuggestion extends CucumberSpringConfiguration {

    @When("{string} gets Suggestions of {string}")
    public void gets_suggestions_of(String userNameCaller, String userName) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+ jwtToken);
        HttpEntity httpEntity = new HttpEntity<>("body",headers);

        ResponseEntity<UserData> userDataResponse=testRestTemplate.exchange("/users?userName="+userName, HttpMethod.GET,httpEntity, UserData.class);
        Long userId = userDataResponse.getBody().getId();
        httpEntity = new HttpEntity<>(headers);
        response = testRestTemplate.exchange("/suggestions/user?userId="+userId, HttpMethod.GET,httpEntity, String.class);
    }


    @When("{string} gets validated Suggestions")
    public void gets_validated_suggestions(String userName) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+ jwtToken);
        HttpEntity httpEntity = new HttpEntity<>(headers);

        response = testRestTemplate.exchange("/suggestions?isValidated=true", HttpMethod.GET,httpEntity, String.class);
    }

    @When("{string} gets non validated Suggestions")
    public void gets_non_validated_suggestions(String userName) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+ jwtToken);
        HttpEntity httpEntity = new HttpEntity<>(headers);

        response = testRestTemplate.exchange("/suggestions?isValidated=false", HttpMethod.GET,httpEntity, String.class);
    }

    @When("{string} gets own Suggestions")
    public void gets_own_suggestions(String string) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+ jwtToken);
        HttpEntity httpEntity = new HttpEntity<>(headers);

        response = testRestTemplate.exchange("/suggestions/me", HttpMethod.GET,httpEntity, String.class);

    }

    @Then("receives list of {int} suggestions")
    public void receives_list_of_suggestions(int number) throws JsonProcessingException{
        String responseBody = (String) response.getBody();
        List<SuggestionData> suggestionDataList = mapper.readValue(responseBody, new TypeReference<List<SuggestionData>>(){});
        assertThat(suggestionDataList)
                .isInstanceOf(List.class)
                .hasOnlyElementsOfType(SuggestionData.class)
                .hasSize(number);
    }

}
