package com.veeva.stepdefinitions;

import com.veeva.clients.PetClient;
import io.cucumber.java.en.*;
import io.restassured.response.Response;

import static org.junit.Assert.assertEquals;

public class PetSteps {

    private Response response;
    private PetClient petClient = new PetClient();
    private int petId;
    private String petName;

    @Given("I create a pet with name {string} and status {string}")
    public void i_create_a_pet(String name, String status) {

        petId = (int) (Math.random() * 100000);
        petName = name;

        response = petClient.createPet(petId, name, status);

        // Extract ID from response
        petId = response.jsonPath().getInt("id");
    }

    @Given("a pet exists with id {int}")
    public void a_pet_exists_with_id(Integer id) {

        petId = id;
    }

    @When("I fetch the created pet")
    public void i_fetch_the_created_pet() {

        response = petClient.getPetById(petId);
    }

    @When("I send a GET request to fetch the pet")
    public void i_send_a_get_request_to_fetch_the_pet() {

        response = petClient.getPetById(petId);
    }

    @Then("the pet status should be {string}")
    public void the_pet_status_should_be(String status) {

        assertEquals(status, response.jsonPath().getString("status"));
    }

    @Then("the pet name should be {string}")
    public void the_pet_name_should_be(String name) {

        assertEquals(name, response.jsonPath().getString("name"));
    }

    @When("I update the pet status to {string}")
    public void i_update_the_pet_status(String status) {

        response = petClient.updatePet(petId, petName, status);
    }

    @When("I delete the pet")
    public void i_delete_the_pet() {

        response = petClient.deletePet(petId);
    }

    @When("I fetch the deleted pet")
    public void i_fetch_the_deleted_pet() {

        response = petClient.getPetById(petId);
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(int statusCode) {

        assertEquals(statusCode, response.getStatusCode());
    }
}