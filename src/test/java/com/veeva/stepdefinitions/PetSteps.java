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
import java.util.Optional;

import static org.junit.Assert.*;

public class PetSteps {
    private static final Logger log = LogManager.getLogger(PetSteps.class);
    private final PetPage petClient = new PetPage();
    private final ScenarioContext ctx;

    public PetSteps(ScenarioContext ctx) {
        this.ctx = ctx;
    }

    @Given("the petstore API is available")
    public void apiReady() {
        log.info("Petstore API configured and ready");
    }

    // Create pet with name and status
    @Given("I create a pet with name {string} and status {string}")
    public void createPet(String name, String status) {
        long id = System.currentTimeMillis();
        Response response = petClient.createPet(id, name, status);
        ctx.set("petId", response.jsonPath().getString("id"));
        ctx.set("petName", name);
        ctx.set("createResponse", response);
        log.info("Created pet id: {}", ctx.get("petId"));
    }

    // Create pet with category
    @Given("I create a new pet with name {string} category {string} and status {string}")
    public void createPetCat(String name, String category, String status) {
        long id = System.currentTimeMillis();
        Response response = petClient.createPetWithCategory(id, name, status, category);
        ctx.set("petId", response.jsonPath().getString("id"));
        ctx.set("petName", name);
        ctx.set("categoryName", category);
        ctx.set("createResponse", response);
        log.info("Created pet with category, id: {}", ctx.get("petId"));
    }

    // Single method handles both pet and pet with category creation validation
    @Then("the pet should be created successfully")
    public void checkCreate() {
        Response r = (Response) ctx.get("createResponse");
        AssertUtils.assertResponseType(r.getStatusCode(), "successful");
        assertNotNull("Pet ID should not be null", r.jsonPath().getString("id"));
        log.info("Pet created successfully with id: {}", r.jsonPath().getString("id"));

        // If category exists in context, validate it too
        String category = ctx.getString("categoryName");
        if (category != null) {
            assertEquals("Category should match", category, r.jsonPath().getString("category.name"));
            log.info("Pet category '{}' validated successfully", category);
        }
    }

    // Fetch created pet
    @When("I fetch the created pet")
    public void fetchPet() {
        Response r = petClient.getPetById((String) ctx.get("petId"));
        ctx.set("getResponse", r);
    }



    // Validate pet name
    @Then("the pet name should be {string}")
    public void checkname(String name) {
        Response r = (Response) ctx.get("getResponse");
        assertEquals("Pet name should match", name, r.jsonPath().getString("name"));
    }

    // Validate pet status
    @Then("the pet status should be {string}")
    public void checkStatus(String status) {
        Response r = (Response) ctx.get("getResponse");
        assertEquals("Pet status should match", status, r.jsonPath().getString("status"));
    }

    // Single method — verifies current status then updates
    @When("I update the pet status from {string} to {string}")
    public void updatestatusfromto(String from, String to) {
        Response current = petClient.getPetById((String) ctx.get("petId"));
        assertEquals("Pet should currently have status " + from,
                from, current.jsonPath().getString("status"));
        log.info("Verified current status is '{}', now updating to '{}'", from, to);

        Response r = petClient.updatePet(
                Long.parseLong((String) ctx.get("petId")),
                ctx.getString("petName"),
                to);
        ctx.set("lastResponse", r);
        ctx.set("updateResponse", r);
        log.info("Updated pet status to '{}'", to);
    }



    // Delete pet — PetPage.deletePet accepts String, no conversion needed
    @When("I delete the pet")
    public void deletepet() {
        Response r = petClient.deletePet((String) ctx.get("petId"));
        ctx.set("lastResponse", r);
        ctx.set("deleteResponse", r);
        log.info("Delete pet with id: {} returned status: {}", ctx.get("petId"), r.getStatusCode());
    }

    // Fetch deleted pet — getPetById accepts String, no conversion needed
    @When("I fetch the deleted pet")
    public void fetchDeleted() {
        Response r = petClient.getPetById((String) ctx.get("petId"));
        ctx.set("getResponse", r);
        ctx.set("lastResponse", r);
    }

    // Fetch pets by status
    @When("I fetch pets with status {string}")
    public void fetchByStatus(String status) {
        List<Pet> pets = petClient.findPetsByStatusAsList(status);
        ctx.set("petsByStatus", pets);
        ctx.set("lastResponse", petClient.findPetsByStatus(status));
        log.info("Found {} pets with status '{}'", pets.size(), status);
    }

    // Validate all pets have expected status
    @Then("all pets should have status {string}")
    public void checkAllStatus(String expectedStatus) {
        @SuppressWarnings("unchecked")
        List<Pet> pets = (List<Pet>) ctx.get("petsByStatus");
        pets.forEach(p ->
                assertEquals("All pets should have status " + expectedStatus,
                        expectedStatus, p.getStatus()));
    }

    // Delegates to AssertUtils — no duplicate switch logic
    @Then("the response should be {string}")
    public void validateResponse(String type) {
        Response r = (Response) ctx.get("lastResponse");
        assertNotNull("Response should not be null", r);
        AssertUtils.assertResponseType(r.getStatusCode(), type);
    }

    // Validate created pet exists in sold pets list
    @Then("the created pet ID should be found in the sold pets list")
    public void checkStream() {
        long createdId = Long.parseLong((String) ctx.get("petId"));

        @SuppressWarnings("unchecked")
        List<Pet> soldPets = (List<Pet>) ctx.get("petsByStatus");

        Optional<Pet> found = soldPets.stream()
                .filter(p -> p.getId() == createdId)
                .findFirst();

        assertTrue("Pet ID " + createdId + " should be in sold pets list via Java Stream",
                found.isPresent());
        log.info("Pet ID {} found in sold list via Stream", createdId);
    }
}