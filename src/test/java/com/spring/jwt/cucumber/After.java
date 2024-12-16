package com.spring.jwt.cucumber;

import com.spring.jwt.configurations.CucumberSpringConfiguration;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;

public class After extends CucumberSpringConfiguration {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Then("tears down database")
    public void tears_down_database(){
       JdbcTestUtils.deleteFromTables(jdbcTemplate,"suggestion");
       JdbcTestUtils.deleteFromTableWhere(jdbcTemplate,"_USER","username != 'Admin' and username != 'Illi'");
    }

    @Given("database is clear")
    public void database_is_clear(){
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"suggestion");
        JdbcTestUtils.deleteFromTableWhere(jdbcTemplate,"_USER","username != 'Admin' and username != 'Illi'");
    }
}
