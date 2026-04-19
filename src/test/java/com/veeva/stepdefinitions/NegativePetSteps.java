package com.veeva.stepdefinitions;

import com.veeva.pages.PetPage;
import com.veeva.context.ScenarioContext;
import com.veeva.utils.AssertUtils;
import io.cucumber.java.en.*;

import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NegativePetSteps {
    private final PetPage petPage = new PetPage();
    private final ScenarioContext ctx;
    private static final Logger log = LogManager.getLogger(NegativePetSteps.class);
    public NegativePetSteps(ScenarioContext ctx) {
        this.ctx = ctx;
    }
    //checking whether a pet doesn't exist with invalid id ,if exists throwing an error
    @Given("a pet does not exist with id {string}")
    public void checkNotExist(String id) {
        ctx.set("nonExistentPetId", id);
        Response checkResponse = petPage.getPetById(id);
        int statusCode = checkResponse.getStatusCode();
        if (AssertUtils.isSuccessful(statusCode)) {
            log.info("Pet with ID [{}] already exists in the system.", id);
            throw new AssertionError("Test data invalid: Pet already exists with ID " + id);
        } else {
            log.info("Pet with ID [{}] does NOT exist. Safe to proceed.", id);
        }
    }
    // fetching a pet which is doesn't exist
    @When("I send a GET request for the non-existing pet")
    public void sendGetNonExist() {
        String id = ctx.getString("nonExistentPetId");
        Response r = petPage.getPetById(id);
        ctx.set("lastResponse", r);
    }
    // sending delete request for non existing pet
    @When("I send a DELETE request for the non-existing pet")
    public void sendDeleteNonExist() {
        String id = ctx.getString("nonExistentPetId");
        Response r = petPage.deletePet(id);
        ctx.set("lastResponse", r);
    }
}