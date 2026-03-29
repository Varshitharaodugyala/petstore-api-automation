package com.veeva.stepdefinitions;

import com.veeva.pages.PetPage;
import com.veeva.context.ScenarioContext;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NegativePetSteps {

    // Logger to print execution details in console / log file
    private static final Logger log = LogManager.getLogger(NegativePetSteps.class);

    // Page (API client) used to make Pet API calls
    private final PetPage petPage = new PetPage();

    // ScenarioContext used to store data between steps
    private final ScenarioContext ctx;

    // Constructor Injection → Cucumber automatically provides ScenarioContext
    public NegativePetSteps(ScenarioContext ctx) {
        this.ctx = ctx;
    }

    // Step to make sure a pet with given id does NOT exist (now supports dynamic data)
    @Given("a pet does not exist with id {string}")
    public void checknotexist(String id) {

        try {
            long petId = Long.parseLong(id);

            ctx.set("nonExistentPetId", petId);
            petPage.deletePet(petId);

            log.info("Ensuring pet id {} does not exist", petId);

        } catch (Exception e) {
            // If invalid (abc, 12.5, etc.)
            ctx.set("nonExistentPetId", id);

            log.info("Invalid pet id format provided: {}", id);
        }
    }

    // Step to send GET request for non-existing pet (handles both valid & invalid ids)
    @When("I send a GET request for the non-existing pet")
    public void sendgetnonexist() {

        Object id = ctx.get("nonExistentPetId");
        Response r;

        if (id instanceof Long) {
            r = petPage.getPetById((Long) id);
        } else {
            r = petPage.getPetByInvalidId(id.toString());
        }

        ctx.set("lastResponse", r);

        log.info("GET non-existing pet response: {}", r.getStatusCode());
    }

    // Step to send DELETE request for non-existing pet
    @When("I send a DELETE request for the non-existing pet")
    public void senddeletenonexist() {

        Object id = ctx.get("nonExistentPetId");
        Response r;

        if (id instanceof Long) {
            r = petPage.deletePet((Long) id);
        } else {
            r = petPage.deletePetByInvalidId(id.toString());
        }

        ctx.set("lastResponse", r);

        log.info("DELETE non-existing pet response: {}", r.getStatusCode());
    }

    // Step to try creating a pet with invalid data
    @Given("I try to create a pet with name {string} and status {string}")
    public void createinvalid(String name, String status) {

        Response r = petPage.createPet(
                System.currentTimeMillis(), name, status);

        ctx.set("lastResponse", r);
    }
}