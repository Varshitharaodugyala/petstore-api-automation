package com.veeva.stepdefinitions;

import com.veeva.clients.StoreClient;
import com.veeva.clients.PetClient;
import io.cucumber.java.en.*;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class InventorySteps {

    private Response inventoryResponse;
    private Response petsResponse;

    private StoreClient storeClient = new StoreClient();
    private PetClient petClient = new PetClient();

    private int inventoryAvailableCount;
    private long petsAvailableCount;

    @When("I fetch the store inventory")
    public void i_fetch_the_store_inventory() {

        inventoryResponse = storeClient.getInventory();
        inventoryAvailableCount = inventoryResponse.jsonPath().getInt("available");
    }

    @Then("the inventory response status should be 200")
    public void the_inventory_response_status_should_be_200() {

        assertEquals(200, inventoryResponse.getStatusCode());
    }

    @Then("the inventory should contain available pets count")
    public void the_inventory_should_contain_available_pets_count() {

        assertTrue(inventoryAvailableCount >= 0);
    }

    @When("I fetch pets with status available")
    public void i_fetch_pets_with_status_available() {

        petsResponse = petClient.findPetsByStatus("available");

        List<Map<String, Object>> pets =
                petsResponse.jsonPath().getList("$");

        petsAvailableCount = pets
                .stream()
                .filter(p -> "available".equals(p.get("status")))
                .count();
    }

    @Then("the pets response status should be 200")
    public void the_pets_response_status_should_be_200() {

        assertEquals(200, petsResponse.getStatusCode());
    }

    @Then("the available pets list should not be empty")
    public void the_available_pets_list_should_not_be_empty() {

        assertTrue(petsAvailableCount > 0);
    }

    @Then("the available pet counts should approximately match")
    public void the_available_pet_counts_should_approximately_match() {

        int difference =
                Math.abs(inventoryAvailableCount - (int) petsAvailableCount);

        assertTrue("Counts differ too much", difference <= 5);
    }
}