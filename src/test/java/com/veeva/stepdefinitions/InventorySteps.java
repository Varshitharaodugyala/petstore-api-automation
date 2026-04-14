package com.veeva.stepdefinitions;

import com.veeva.pages.StorePage;
import com.veeva.context.ScenarioContext;
import com.veeva.models.Pet;
import io.cucumber.java.en.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import com.veeva.pages.PetPage;
import com.veeva.utils.AssertUtils;
import io.restassured.response.Response;

// Step Definition class for Inventory related scenarios
public class InventorySteps {

    private static final Logger log = LogManager.getLogger(InventorySteps.class);
    private final StorePage storeClient = new StorePage();
    private final ScenarioContext ctx;

    public InventorySteps(ScenarioContext ctx) {
        this.ctx = ctx;
    }

    // ================= INVENTORY =================

    @When("I fetch the store inventory")
    public void fetchinventory() {

        Response response = storeClient.getInventory();

        ctx.set("inventoryResponse", response);

        // store full inventory map (available, sold, pending)
        ctx.set("inventoryMap", response.jsonPath().getMap("$"));

        log.info("Inventory fetched: {}", ctx.get("inventoryMap"));
    }

    @Then("the inventory response should be successful")
    public void validateInventoryStatus() {

        Response response = (Response) ctx.get("inventoryResponse");

        assertNotNull("Response should not be null", response);


        AssertUtils.assertResponseType(response.getStatusCode(), "successful");

        //  merged inventory validation
        Map<String, Integer> inventory = (Map<String, Integer>) ctx.get("inventoryMap");

        assertNotNull("Inventory should not be null", inventory);
        assertFalse("Inventory should not be empty", inventory.isEmpty());
    }

    // ================= PET STATUS =================

    private final PetPage petClient = new PetPage();

    @When("I fetch pets with status {string}")
    public void fetchinventoryPets(String status) {
        log.info("🔎 Fetching pet list for status: {}", status);
        Response response = petClient.findPetsByStatus(status);

        // 💡 SAVE TO ALL KEYS TO PREVENT CONTEXT ERRORS
        ctx.set("lastResponse", response);
        ctx.set("petsResponse", response);

        List<Pet> pets = response.jsonPath().getList("", Pet.class);
        ctx.set("petsByStatus", pets);
    }

    //  MERGED METHOD (response + list validation)
    @Then("the pets response should be successful and {string} pets list should not be empty")
    public void validatePetsResponseAndList(String status) {
        // Check both possible keys to ensure we find the response
        Response response = (Response) ctx.get("petsResponse");
        if (response == null) {
            response = (Response) ctx.get("lastResponse");
        }

        assertNotNull("❌ Context Error: No response found! Check if 'I fetch pets' ran correctly.", response);
        AssertUtils.assertResponseType(response.getStatusCode(), "successful");

        List<?> pets = (List<?>) ctx.get("petsByStatus");
        assertNotNull("❌ Context Error: Pets list was null!", pets);
        assertFalse(status + " pets list should not be empty", pets.isEmpty());
    }

    // ================= COUNT COMPARISON =================

    @Then("the pet counts for {string} should approximately match")
    public void comparecounts(String status) {

        Map<String, Integer> inventory = (Map<String, Integer>) ctx.get("inventoryMap");
        List<?> pets = (List<?>) ctx.get("petsByStatus");

        int inventoryCount = inventory.getOrDefault(status, 0);
        int findByStatusCount = pets.size();

        log.info("Inventory {}: {} | API {}: {}",
                status, inventoryCount, status, findByStatusCount);

        int diff = Math.abs(inventoryCount - findByStatusCount);
        double tolerance = inventoryCount > 0 ? inventoryCount * 0.20 : 10;

        log.info("Diff: {} | Allowed tolerance: {}", diff, tolerance);

        assertTrue("Counts should be within tolerance", diff <= tolerance);
    }
}