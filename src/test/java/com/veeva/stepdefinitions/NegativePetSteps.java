package com.veeva.stepdefinitions;

import com.veeva.clients.PetClient;
import com.veeva.context.ScenarioContext;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NegativePetSteps {

    private static final Logger log = LogManager.getLogger(NegativePetSteps.class);
    private final PetClient petClient = new PetClient();
    private final ScenarioContext ctx;

    public NegativePetSteps(ScenarioContext ctx) {
        this.ctx = ctx;
    }

    @Given("a pet does not exist with id {int}")
    public void a_pet_does_not_exist_with_id(Integer id) {
        ctx.set("nonExistentPetId", (long) id);
        petClient.deletePet(id);
        log.info("Ensuring pet id {} does not exist", id);
    }

    @When("I send a GET request for the non-existing pet")
    public void i_send_get_request_for_non_existing_pet() {
        Response r = petClient.getPetById(ctx.getLong("nonExistentPetId"));
        ctx.set("lastResponse", r);
        log.info("GET non-existing pet response: {}", r.getStatusCode());
    }

    @When("I send a DELETE request for the non-existing pet")
    public void i_send_delete_request_for_non_existing_pet() {
        Response r = petClient.deletePet(ctx.getLong("nonExistentPetId"));
        ctx.set("lastResponse", r);
        log.info("DELETE non-existing pet response: {}", r.getStatusCode());
    }

    @Given("I try to create a pet with name {string} and status {string}")
    public void i_try_to_create_pet_invalid(String name, String status) {
        Response r = petClient.createPet(
                System.currentTimeMillis(), name, status);
        ctx.set("lastResponse", r);
    }
}