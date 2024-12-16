package com.spring.jwt.cucumber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spring.jwt.configurations.CucumberSpringConfiguration;
import com.spring.jwt.data.SuggestionData;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONException;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserCreatesSuggestions extends CucumberSpringConfiguration {


    @When("{string} creates new Suggestion {string}")
    public void creates_new_suggestion(String userName, String text) throws JSONException {

        HttpHeaders headers = new HttpHeaders();
        JSONObject newSuggestion = new JSONObject();

        headers.add("Authorization","Bearer "+ jwtToken);

        newSuggestion.put("text",text);

        HttpEntity httpEntity = new HttpEntity<>(newSuggestion,headers);

        response=testRestTemplate.exchange("/suggestions", HttpMethod.POST,httpEntity, SuggestionData.class);

    }
    @Then("receives suggestion {string}")
    public void receives_suggestion(String text) throws JsonProcessingException {

        SuggestionData suggestion = mapper.convertValue(response.getBody(), SuggestionData.class);
        assertEquals(text, suggestion.getText());
    }

    @When("{string} validates {string}")
    public void validates(String userName, String text) {

        HttpHeaders headers = new HttpHeaders();
        JSONObject updateSuggestion = new JSONObject();

        headers.add("Authorization","Bearer "+ jwtToken);
        headers.add("Content-Type","application/json");

        updateSuggestion.put("text",text);
        updateSuggestion.put("isValidated",true);

        HttpEntity<JSONObject> httpEntity = new HttpEntity<JSONObject>(updateSuggestion,headers);
        response=testRestTemplate.exchange("/suggestions?text="+text, HttpMethod.PUT,httpEntity, String.class);

    }

    @Given("{string} created new suggestion {string}")
    public void created_new_suggestion(String userName, String text) throws JSONException {

        creates_new_suggestion(userName,text);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}
