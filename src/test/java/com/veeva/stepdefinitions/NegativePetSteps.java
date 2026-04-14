package com.veeva.stepdefinitions;

import com.veeva.pages.PetPage;
import com.veeva.context.ScenarioContext;
import io.cucumber.java.en.*;
import io.restassured.response.Response;

public class NegativePetSteps {
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
        }
    }

    @When("I send a GET request for the non-existing pet")
    public void sendGetNonExist() {
        String id = ctx.getString("nonExistentPetId");
        Response r = petPage.getPetById(id);
        ctx.set("lastResponse", r);
    }

    @When("I send a DELETE request for the non-existing pet")
    public void sendDeleteNonExist() {
        String id = ctx.getString("nonExistentPetId");
        Response r = petPage.deletePet(id);
        ctx.set("lastResponse", r);
    }
}