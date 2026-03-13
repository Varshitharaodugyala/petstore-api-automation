package com.veeva.stepdefinitions;

import com.veeva.clients.PetClient;
import io.cucumber.java.en.*;
import io.restassured.response.Response;

import static org.junit.Assert.assertEquals;

public class NegativePetSteps {

    private PetClient petClient = new PetClient();
    private Response response;
    private int petId;

    // Use a unique step for non-existing pet creation
    @Given("a pet does not exist with id {int}")
    public void set_non_existing_pet_id(Integer id) {
        petId = id;
        // Ensure it's deleted to guarantee it doesn't exist
        petClient.deletePet(petId);
    }

    // Unique step for GET request for negative scenarios
    @When("I send a GET request for the non-existing pet")
    public void i_send_a_get_request_for_non_existing_pet() {
        response = petClient.getPetById(petId);
    }

    // DELETE request step (can be reused if needed)
    @When("I send a DELETE request for the non-existing pet")
    public void i_send_a_delete_request_for_non_existing_pet() {
        response = petClient.deletePet(petId);
    }

    // Create pet with invalid data (optional negative scenario)
    @Given("I try to create a pet with name {string} and status {string}")
    public void i_try_to_create_a_pet_with_invalid_data(String name, String status) {
        response = petClient.createPet((int) (Math.random() * 100000), name, status);
    }

    // Status code validation (shared)
    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(int statusCode) {
        assertEquals(statusCode, response.getStatusCode());
    }
}