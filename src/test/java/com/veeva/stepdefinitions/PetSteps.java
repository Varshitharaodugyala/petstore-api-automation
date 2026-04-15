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
    private static final Logger log = LogManager.getLogger(PetSteps.class);
    private final PetPage petClient = new PetPage();
    private final ScenarioContext ctx;

    public PetSteps(ScenarioContext ctx) {
        this.ctx = ctx;
    }

    @Given("the petstore API is available")
    public void apiReady() {
        log.info("Checking API availability...");
    }

    private void saveContext(Response r, String name) {
        if (r == null) return;
        ctx.set("lastResponse", r);
        ctx.set("petsResponse", r); // Syncing key for InventorySteps

        if (name != null && !name.isEmpty()) ctx.set("petName", name);

        try {
            Object id = r.jsonPath().get("id");
            if (id != null) {
                ctx.set("petId", String.valueOf(id));
            }
        } catch (Exception e) {
            log.warn("Could not parse ID from response body");
        }
    }

    // Matches both Standard and TC4 Creation
    // Method 1: Matches TC1 - Standard Create (2 parameters)
    @When("I create a pet with name {string} and status {string}")
    public void createPetStandard(String name, String status) {
        long id = System.currentTimeMillis() % 1000000;
        log.info(" TC1: Creating standard pet: {} with status: {}", name, status);
        Response r = petClient.createPet(id, name, status);
        saveContext(r, name);
    }

    // Method 2: Matches TC4 - Create with Category (3 parameters)
    @Given("I create a new pet with name {string} category {string} and status {string}")
    public void createPetWithCategoryStep(String name, String category, String status) {
        long id = System.currentTimeMillis() % 1000000;
        log.info("🏗️ TC4: Creating pet with category: {} | Name: {}", category, name);
        Response r = petClient.createPetWithCategory(id, name, status, category);
        saveContext(r, name);
    }

    @Then("the pet should be created successfully")
    public void verifyCreated() {
        Response r = (Response) ctx.get("lastResponse");
        assertEquals(200, r.getStatusCode());
    }

    @When("I fetch the created pet")
    public void fetchPet() throws InterruptedException {
        String petId = ctx.getString("petId");
        Response r = null;

        for (int i = 0; i < 6; i++) { // Try for up to 18 seconds
            r = petClient.getPetById(petId);
            if (r.getStatusCode() == 200 && r.jsonPath().get("name") != null) break;
            log.warn("⏳ Pet {} not ready, retrying (Attempt {})...", petId, i + 1);
            Thread.sleep(3000);
        }
        saveContext(r, r.jsonPath().getString("name"));
    }

    @And("the pet name should be {string}")
    public void verifyPetName(String expectedName) {
        Response r = (Response) ctx.get("lastResponse");
        assertEquals(expectedName, r.jsonPath().getString("name"));
    }

    @And("the pet status should be {string}")
    public void verifyPetStatus(String expectedStatus) {
        Response r = (Response) ctx.get("lastResponse");
        assertEquals(expectedStatus, r.jsonPath().getString("status"));
    }

    @And("I update the pet status from {string} to {string}")
    public void updateStatus(String from, String to) throws InterruptedException {
        Thread.sleep(1500);
        Response r = petClient.updatePet(ctx.getString("petId"), ctx.getString("petName"), to);
        saveContext(r, ctx.getString("petName"));
    }

    @Then("the response should be {string}")
    public void validateResponse(String type) {
        Response r = (Response) ctx.get("lastResponse");
        AssertUtils.assertResponseType(r.getStatusCode(), type);
    }

    @When("I delete the pet")
    public void deletePet() throws InterruptedException {
        String petId = ctx.getString("petId");

        // Give the API a significant pause to finish the 'Update' sync
        log.info("⏳ Cooling down before deleting pet ID: {}", petId);
        Thread.sleep(4000);

        Response r = petClient.deletePet(petId);

        // If it still fails with 404, try ONE more time after another 3s
        if (r.getStatusCode() == 404) {
            log.warn("⚠️ Initial delete failed with 404, retrying final attempt...");
            Thread.sleep(3000);
            r = petClient.deletePet(petId);
        }

        ctx.set("lastResponse", r);
    }

    @And("I fetch the deleted pet")
    public void fetchDeleted() throws InterruptedException {
        Thread.sleep(2000);
        Response r = petClient.getPetById(ctx.getString("petId"));
        ctx.set("lastResponse", r);
    }

    @When("I fetch pets with status as {string}")
    public void fetchByStatus(String status) {
        Response r = petClient.findPetsByStatus(status);
        // CRITICAL: Save to both keys so InventorySteps can see it
        ctx.set("lastResponse", r);
        ctx.set("petsResponse", r);

        List<Pet> list = r.jsonPath().getList("", Pet.class);
        ctx.set("petsByStatus", list);
        log.info("Fetched {} pets with status {}", list.size(), status);
    }

    @And("all pets should have status {string}")
    public void verifyAllStatuses(String status) {
        List<Pet> pets = (List<Pet>) ctx.get("petsByStatus");
        assertTrue(pets.stream().allMatch(p -> p.getStatus().equals(status)));
    }

    @Then("the created pet ID should be found in the sold pets list")
    public void verifyInSoldList() throws InterruptedException {
        String targetId = ctx.getString("petId");
        boolean found = false;
        for (int i = 0; i < 5; i++) {
            Response r = petClient.findPetsByStatus("sold");
            List<Object> ids = r.jsonPath().getList("id");
            found = ids.stream().anyMatch(id -> String.valueOf(id).equals(targetId));
            if (found) break;
            Thread.sleep(2000);
        }
        assertTrue("Pet ID " + targetId + " not found in sold list.", found);
    }
}