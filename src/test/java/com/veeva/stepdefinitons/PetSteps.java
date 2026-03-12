package com.veeva.stepdefinitions;

import com.veeva.clients.PetClient;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;

import static org.junit.Assert.assertEquals;

public class PetSteps {

    private Response response;
    private PetClient petClient = new PetClient();
    private int petId = 101;

    @Given("I create a new pet")
    public void i_create_a_new_pet() {

        response = petClient.createPet(petId, "dog", "available");
    }

    @Given("a pet exists with id {int}")
    public void a_pet_exists_with_id(int id) {

        this.petId = id;
    }

    @When("I fetch the created pet")
    public void i_fetch_the_created_pet() {

        response = petClient.getPetById(petId);
    }

    @When("I send a GET request to fetch the pet")
    public void i_send_a_get_request_to_fetch_the_pet() {

        response = petClient.getPetById(petId);
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(int statusCode) {

        assertEquals(statusCode, response.getStatusCode());
    }
}