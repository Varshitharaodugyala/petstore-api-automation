package com.veeva.stepdefinitions;

import com.veeva.pages.PetPage;
import com.veeva.context.ScenarioContext;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NegativePetSteps {
    private static final Logger log = LogManager.getLogger(NegativePetSteps.class);
    private final PetPage petPage = new PetPage();
    private final ScenarioContext ctx;

    public NegativePetSteps(ScenarioContext ctx) {
        this.ctx = ctx;
    }

    @Given("a pet does not exist with id {string}")
    public void checkNotExist(String id) {
        ctx.set("nonExistentPetId", id);
        Response checkResponse = petPage.getPetById(id);
        if (checkResponse.getStatusCode() == 200) {
            petPage.deletePet(id);
            log.info("Pet with id {} existed and was deleted to ensure clean state", id);
        } else {
            log.info("Pet with id {} does not exist, no deletion needed", id);
        }
    }

    @When("I send a GET request for the non-existing pet")
    public void sendGetNonExist() {
        String id = (String) ctx.get("nonExistentPetId");
        Response r = petPage.getPetById(id);
        ctx.set("lastResponse", r);
        log.info("GET non-existing pet response: {}", r.getStatusCode());
    }

    @When("I send a DELETE request for the non-existing pet")
    public void sendDeleteNonExist() {
        String id = (String) ctx.get("nonExistentPetId");
        Response r = petPage.deletePet(id);
        ctx.set("lastResponse", r);
        log.info("DELETE non-existing pet response: {}", r.getStatusCode());
    }
}