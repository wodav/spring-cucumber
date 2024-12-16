package com.spring.jwt.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

@Suite
@SelectClasspathResource("features")
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@CucumberContextConfiguration
public class CucumberSpringConfiguration {
    @Autowired
    protected TestRestTemplate testRestTemplate;

    protected ObjectMapper mapper = new ObjectMapper();

    protected static ResponseEntity response;
    protected static String jwtToken;
}
