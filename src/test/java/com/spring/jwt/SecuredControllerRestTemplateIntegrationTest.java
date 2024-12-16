package com.spring.jwt;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
/*
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SecuredControllerRestTemplateIntegrationTest {

    @Autowired
    private TestRestTemplate template;

    // ... other methods

    @Test
    public void givenAuthRequestOnPrivateService_shouldSucceedWith200() throws Exception {
        ResponseEntity<String> result = template.withBasicAuth("user", "password")
                .getForEntity("/login", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    public void givenAuthRequestOnPrivateService_shouldSucceedWith404() throws Exception {
        ResponseEntity<String> result = template.withBasicAuth("userr", "password")
                .getForEntity("/login", String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }
}
*/

