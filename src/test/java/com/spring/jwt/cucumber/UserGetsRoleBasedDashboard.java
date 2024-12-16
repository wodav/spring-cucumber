package com.spring.jwt.cucumber;

import com.spring.jwt.configurations.CucumberSpringConfiguration;
import com.spring.jwt.response.SignInResponse;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserGetsRoleBasedDashboard extends CucumberSpringConfiguration {

  //  private ResponseEntity response;
  //  ResponseEntity<SignInResponse> jwtResponse;



    @When("{string} calls Endpoint of Dashboard with {string}")
    public void givenRestCallWithBearerTokenGetDashboard_shouldSuccedWith200(String userName, String endpoint)throws Exception{

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+ jwtToken);
        HttpEntity httpEntity = new HttpEntity<>("body",headers);

        response=testRestTemplate.exchange(endpoint, HttpMethod.GET,httpEntity, String.class);

    }

    @Then ("he gets back My Dashboard: {string}")
    public void heGetsBackUserDashboard(String string){
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals("My Dashboard: " + string, response.getBody().toString().replace("\"",""));
    }
}
