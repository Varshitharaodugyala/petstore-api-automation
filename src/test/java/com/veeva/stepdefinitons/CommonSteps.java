package com.veeva.stepdefinitions;

import io.cucumber.java.en.Then;
import io.restassured.response.Response;

import static org.junit.Assert.assertEquals;

public class CommonSteps {

    public static Response response;

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(int statusCode) {

        assertEquals(statusCode, response.getStatusCode());
    }
}