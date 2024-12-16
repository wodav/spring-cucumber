package com.spring.jwt.cucumber;

import com.spring.jwt.configurations.CucumberSpringConfiguration;
import io.cucumber.java.en.Then;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Responses extends CucumberSpringConfiguration {

    @Then("receives {string}")
    public void receives(String statusCode) {
        switch (statusCode){
            case "200 OK": assertEquals(HttpStatus.OK, response.getStatusCode()); break;
            case "401 UNAUTHORIZED": assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode()); break;
            case "403 Forbidden": assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode()); break;
            case "204 NO_CONTENT": assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode()); break;
            case "409 CONFLICT": assertEquals(HttpStatus.CONFLICT, response.getStatusCode()); break;
            case "201 CREATED": assertEquals(HttpStatus.CREATED, response.getStatusCode()); break;
            default: throw new NullPointerException("Case not created in switch");
        }
    }

    @Then("receives error {string}")
    public void receives_error(String errorMessage) {

        assertEquals(errorMessage, response.getBody());
    }

}
