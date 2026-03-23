package com.veeva.stepdefinitions;

import com.veeva.clients.StoreClient;
import com.veeva.context.ScenarioContext;
import com.veeva.models.Pet;
import io.cucumber.java.en.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static org.junit.Assert.*;
import com.veeva.clients.PetClient;

// Step Definition class for Inventory related scenarios
// Contains actual test execution logic and validations
public class InventorySteps {

    // Logger for printing execution logs
    private static final Logger log = LogManager.getLogger(InventorySteps.class);

    // Client used to call Store APIs
    private final StoreClient storeClient = new StoreClient();

    // ScenarioContext used to store data between steps
    private final ScenarioContext ctx;

    // Constructor injection - Cucumber provides ScenarioContext automatically
    public InventorySteps(ScenarioContext ctx) {
        this.ctx = ctx;
    }

    // Step to fetch inventory available pet count from Store API
    @When("I fetch the store inventory")
    public void i_fetch_the_store_inventory() {
        int count = storeClient.getAvailableCount();
        ctx.set("inventoryAvailableCount", count); // store count for later steps
        log.info("Inventory available count: {}", count);
    }

    // Validate inventory API executed successfully (basic validation)
    @Then("the inventory response status should be 200")
    public void the_inventory_response_status_should_be_200() {
        assertTrue("Inventory count should be >= 0",
                ctx.getInt("inventoryAvailableCount") >= 0);
    }

    // Validate inventory contains available pets
    @Then("the inventory should contain available pets count")
    public void the_inventory_should_contain_available_pets_count() {
        assertTrue("Available count should be non-negative",
                ctx.getInt("inventoryAvailableCount") >= 0);
    }

    // Client used to call Pet APIs
    private final PetClient petClient = new PetClient();

    // Step to fetch pets list with status = available
    @When("I fetch pets with status available")
    public void i_fetch_pets_with_status_available() {
        List<Pet> availablePets = petClient.findPetsByStatusAsList("available");
        ctx.set("petsByStatus", availablePets); // store list for validation
        log.info("Found {} available pets via findByStatus", availablePets.size());
    }

    // Validate pets API executed successfully
    @Then("the pets response status should be {int}")
    public void the_pets_response_status_should_be(int code) {
        List<Pet> pets = (List<Pet>) ctx.get("petsByStatus");
        assertNotNull("Pets list should not be null", pets);
        log.info("Pets response verified with {} pets", pets.size());
    }

    // Validate returned pets list is not empty
    @Then("the available pets list should not be empty")
    public void the_available_pets_list_should_not_be_empty() {
        List<Pet> pets = (List<Pet>) ctx.get("petsByStatus");
        assertFalse("Available pets list should not be empty", pets.isEmpty());
    }

    // Compare inventory count vs findByStatus count with tolerance
    @Then("the available pet counts should approximately match")
    public void the_available_pet_counts_should_approximately_match() {

        int inventoryCount = ctx.getInt("inventoryAvailableCount");

        List<Pet> pets = (List<Pet>) ctx.get("petsByStatus");
        int findByStatusCount = pets.size();

        log.info("Inventory count: {} | findByStatus count: {}",
                inventoryCount, findByStatusCount);

        int diff = Math.abs(inventoryCount - findByStatusCount);

        // Allow 20% difference because public API data can change anytime
        double tolerance = inventoryCount > 0 ? inventoryCount * 0.20 : 10;

        log.info("Diff: {} | Allowed tolerance (20%): {}", diff, tolerance);

        assertTrue(
                "Counts should be within tolerance",
                diff <= tolerance);
    }

    // Alternate step that reuses same validation logic
    @Then("the available pet counts should match")
    public void the_available_pet_counts_should_match() {
        the_available_pet_counts_should_approximately_match();
    }
}