package com.veeva.stepdefinitions;

import com.veeva.pages.StorePage;
import com.veeva.context.ScenarioContext;
import com.veeva.models.Pet;
import io.cucumber.java.en.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static org.junit.Assert.*;
import com.veeva.pages.PetPage;
import io.restassured.response.Response;
// Step Definition class for Inventory related scenarios
// Contains actual test execution logic and validations
public class InventorySteps {

    private static final Logger log = LogManager.getLogger(InventorySteps.class);
    private final StorePage storeClient = new StorePage();
    private final ScenarioContext ctx;

    public InventorySteps(ScenarioContext ctx) {
        this.ctx = ctx;
    }

    // Step to fetch inventory available pet count from Store API
    @When("I fetch the store inventory")
    public void fetchinventory() {
        int count = storeClient.getAvailableCount();
        ctx.set("inventoryAvailableCount", count); // store count for later steps
        log.info("Inventory available count: {}", count);
    }

    @Then("the inventory response should be successful")
    public void validateInventoryStatus() {

        Response response = (Response) ctx.get("inventoryResponse");

        assertNotNull("Response should not be null", response);

        assertEquals("Expected status code 200", 200, response.getStatusCode());
    }
    // Validate inventory contains available pets
    @Then("the inventory should contain available pets count")
    public void checkinventorycount() {
        assertTrue("Available count should be non-negative",
                ctx.getInt("inventoryAvailableCount") >= 0);
    }

    // Client used to call Pet APIs
    private final PetPage petClient = new PetPage();

    // Step to fetch pets list with status = available
    @When("I fetch pets with status available")
    public void fetchavailable() {
        List<Pet> availablePets = petClient.findPetsByStatusAsList("available");
        ctx.set("petsByStatus", availablePets); // store list for validation
        log.info("Found {} available pets via findByStatus", availablePets.size());
    }
    @Then("the pets response should be successful")
    public void validatePetsStatus() {

        int statusCode = ctx.getInt("petsStatusCode");

        assertEquals("Expected status code 200", 200, statusCode);
    }


    // Validate pets API executed successfully
    @Then("the available pets list should not be empty")
    public void validatePetsList() {
        List<?> pets = (List<?>) ctx.get("petsByStatus");

        assertNotNull("Pets list should not be null", pets);
        assertFalse("Pets list should not be empty", pets.isEmpty());

        log.info("Pets list validation passed with {} pets", pets.size());
    }

    // Compare inventory count vs findByStatus count with tolerance
    // Allow 20% difference because public API data can change anytime
    @Then("^the available pet counts should approximately match$")
    public void comparecounts() {
        int inventoryCount = ctx.getInt("inventoryAvailableCount");
        List<?> pets = (List<?>) ctx.get("petsByStatus");
        int findByStatusCount = pets.size();
        log.info("Inventory count: {} | findByStatus count: {}",
                inventoryCount, findByStatusCount);
        int diff = Math.abs(inventoryCount - findByStatusCount);
        double tolerance = inventoryCount > 0 ? inventoryCount * 0.20 : 10;
        log.info("Diff: {} | Allowed tolerance (20%): {}", diff, tolerance);
        assertTrue(
                "Counts should be within tolerance",
                diff <= tolerance);
    }

    // Alternate step that reuses same validation logic
    @Then("the available pet counts should match")
    public void matchcounts() {
        comparecounts();
    }
}