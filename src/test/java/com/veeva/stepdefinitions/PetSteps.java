package com.veeva.stepdefinitions;

import com.veeva.pages.PetPage;
import com.veeva.context.ScenarioContext;
import com.veeva.models.Pet;
import com.veeva.utils.AssertUtils;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static org.junit.Assert.*;

public class PetSteps {

    private final PetPage petClient = new PetPage();
    private final ScenarioContext ctx;
    private static final Logger log = LogManager.getLogger(NegativePetSteps.class);
    public PetSteps(ScenarioContext ctx) {
        this.ctx = ctx;
    }
    @Given("the petstore API is available")
    public void apiReady() {
        log.info("Checking API availability...");
    }

    private void save(Response r, String name) {
        ctx.set("lastResponse", r);
        if (name != null) {
            ctx.set("petName", name);
        }
        if (r != null) {
            Long id = r.jsonPath().getLong("id");
            if (id != null) {
                ctx.set("petId", String.valueOf(id));
            }
        }

    }

    @When("I create a pet with name {string} and status {string}")
    public void createPet(String name, String status) {
        long id = System.currentTimeMillis();
        Response r = petClient.createPet(id, name, status);
        assertNotNull(r);
        AssertUtils.assertResponseType(r.getStatusCode(), "successful");
        save(r, name);
    }

    @Given("I create a new pet with name {string} category {string} and status {string}")
    public void createPetWithCategory(String name, String category, String status) {
        long id = System.currentTimeMillis();
        Response r = petClient.createPetWithCategory(id, name, status, category);
        AssertUtils.assertResponseType(r.getStatusCode(), "successful");
        save(r, name);
        ctx.set("petCategory", category);
    }

    @Then("the pet should be created successfully")
    public void verifyCreated() {
        Response r = (Response) ctx.get("lastResponse");
        AssertUtils.assertResponseType(r.getStatusCode(), "successful");
    }

    @When("I fetch the created pet")
    public void fetchPet() {
        Response r = petClient.getPetByIdWithRetry(ctx.getString("petId"));
        assertNotNull(r);
        save(r, r.jsonPath().getString("name"));
    }

    @And("the pet name should be {string}")
    public void checkName(String name) {
        Response r = (Response) ctx.get("lastResponse");
        assertEquals(name, r.jsonPath().getString("name"));
    }

    @And("the pet status should be {string}")
    public void checkStatus(String status) {
        Response r = (Response) ctx.get("lastResponse");
        assertEquals(status, r.jsonPath().getString("status"));
    }

    //  UPDATE

    @And("I update the pet status from {string} to {string}")
    public void updateStatus(String from, String to) {
        Response r = petClient.updatePet(
                ctx.getString("petId"),
                ctx.getString("petName"),
                to
        );
        AssertUtils.assertResponseType(r.getStatusCode(), "successful");
        save(r, ctx.getString("petName"));
    }

    //  DELETE

    @When("I delete the pet")
    public void deletePet() {
        Response r = petClient.deletePet(ctx.getString("petId"));
        assertNotNull(r);
        ctx.set("lastResponse", r);
    }

    @And("I fetch the deleted pet")
    public void fetchDeleted() {
        Response r = petClient.waitUntilPetDeleted(ctx.getString("petId"));
        ctx.set("lastResponse", r);
    }

    @Then("the response should be {string}")
    public void validateResponse(String type) {
        Response r = (Response) ctx.get("lastResponse");
        AssertUtils.assertResponseType(r.getStatusCode(), "successful");
    }

    @When("I fetch pets with status as {string}")
    public void fetchByStatus(String status) {
        Response r = petClient.findPetsByStatus(status);
        List<Pet> list = r.jsonPath().getList("", Pet.class);
        ctx.set("petsByStatus", list);
        ctx.set("lastResponse", r);
    }

    @And("all pets should have status {string}")
    public void checkAllStatus(String status) {
        List<Pet> pets = (List<Pet>) ctx.get("petsByStatus");
        assertNotNull(pets);
        assertFalse(pets.isEmpty());
        pets.forEach(p -> assertEquals(status, p.getStatus()));
    }

    @Then("the created pet ID should be found in the sold pets list")
    public void checkSoldList() {
        Long targetId = Long.valueOf(ctx.getString("petId"));
        Response r = petClient.findPetsByStatus("sold");
        List<Long> ids = r.jsonPath().getList("id", Long.class);
        boolean found = ids.stream().anyMatch(id -> id.equals(targetId));
        assertTrue("Pet ID " + targetId + " not found in sold list", found);
    }
}