package com.veeva.stepdefinitions;
import static org.junit.Assert.assertTrue;
import com.veeva.clients.StoreClient;
import com.veeva.clients.PetClient;
import io.cucumber.java.en.*;
import io.restassured.response.Response;

import static org.junit.Assert.assertEquals;

public class InventorySteps {

    private Response inventoryResponse;
    private Response petsResponse;

    private StoreClient storeClient = new StoreClient();
    private PetClient petClient = new PetClient();

    private int inventoryAvailableCount;
    private int petsAvailableCount;

    @When("I fetch the store inventory")
    public void i_fetch_the_store_inventory() {

        inventoryResponse = storeClient.getInventory();

        inventoryAvailableCount = inventoryResponse.jsonPath().getInt("available");
    }

    @When("I fetch pets with status available")
    public void i_fetch_pets_with_status_available() {

        petsResponse = petClient.findPetsByStatus("available");

        petsAvailableCount = petsResponse.jsonPath().getList("$").size();
    }

    @Then("the available pet counts should match")
    public void the_available_pet_counts_should_match() {

        int difference = Math.abs(inventoryAvailableCount - (int)petsAvailableCount);

        assertTrue("Inventory and pet list count mismatch", difference <= 5);
    }
}