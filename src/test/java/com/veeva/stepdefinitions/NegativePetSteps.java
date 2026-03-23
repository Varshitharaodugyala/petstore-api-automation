package com.veeva.stepdefinitions;

import com.veeva.clients.PetClient;
import com.veeva.context.ScenarioContext;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NegativePetSteps {

    // Logger to print execution details in console / log file
    private static final Logger log = LogManager.getLogger(NegativePetSteps.class);

    // Client used to make Pet API calls
    private final PetClient petClient = new PetClient();

    // ScenarioContext used to store data between steps
    private final ScenarioContext ctx;

    // Constructor Injection → Cucumber automatically provides ScenarioContext
    public NegativePetSteps(ScenarioContext ctx) {
        this.ctx = ctx;
    }

    // Step to make sure a pet with given id does NOT exist
    @Given("a pet does not exist with id {int}")
    public void a_pet_does_not_exist_with_id(Integer id) {

        // Store the non-existing pet id in ScenarioContext
        ctx.set("nonExistentPetId", (long) id);

        // Try deleting the pet (if exists it will be removed)
        petClient.deletePet(id);

        // Log information
        log.info("Ensuring pet id {} does not exist", id);
    }

    // Step to send GET request for non-existing pet
    @When("I send a GET request for the non-existing pet")
    public void i_send_get_request_for_non_existing_pet() {

        // Call GET /pet/{id}
        Response r = petClient.getPetById(
                ctx.getLong("nonExistentPetId"));

        // Store full response for later validations
        ctx.set("lastResponse", r);

        // Log response status
        log.info("GET non-existing pet response: {}", r.getStatusCode());
    }

    // Step to send DELETE request for non-existing pet
    @When("I send a DELETE request for the non-existing pet")
    public void i_send_delete_request_for_non_existing_pet() {

        // Call DELETE /pet/{id}
        Response r = petClient.deletePet(
                ctx.getLong("nonExistentPetId"));

        // Store response for validation
        ctx.set("lastResponse", r);

        // Log response status
        log.info("DELETE non-existing pet response: {}", r.getStatusCode());
    }

    // Step to try creating a pet with invalid data
    @Given("I try to create a pet with name {string} and status {string}")
    public void i_try_to_create_pet_invalid(String name, String status) {

        // Create pet with unique id using current timestamp
        Response r = petClient.createPet(
                System.currentTimeMillis(), name, status);

        // Store response to validate status / error message later
        ctx.set("lastResponse", r);
    }
}