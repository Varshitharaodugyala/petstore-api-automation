package com.veeva.stepdefinitions;

import com.veeva.clients.PetClient;
import io.cucumber.java.en.*;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class PetSteps {

    private PetClient petClient = new PetClient();
    private int petId;
    private String petName;

    @Given("I create a pet with name {string} and status {string}")
    public void i_create_a_pet(String name, String status) {

        petId = (int) (Math.random() * 100000);
        petName = name;

        CommonSteps.response =
                petClient.createPet(petId, name, status);

        petId = CommonSteps.response.jsonPath().getInt("id");
    }

    @When("I fetch the created pet")
    public void i_fetch_the_created_pet() {

        CommonSteps.response =
                petClient.getPetById(petId);
    }

    @Then("the pet status should be {string}")
    public void the_pet_status_should_be(String status) {

        assertEquals(status,
                CommonSteps.response.jsonPath().getString("status"));
    }

    @Then("the pet name should be {string}")
    public void the_pet_name_should_be(String name) {

        assertEquals(name,
                CommonSteps.response.jsonPath().getString("name"));
    }

    @When("I update the pet status to {string}")
    public void i_update_the_pet_status(String status) {

        CommonSteps.response =
                petClient.updatePet(petId, petName, status);
    }

    @When("I delete the pet")
    public void i_delete_the_pet() {

        CommonSteps.response =
                petClient.deletePet(petId);
    }

    @When("I fetch the deleted pet")
    public void i_fetch_the_deleted_pet() {

        CommonSteps.response =
                petClient.getPetById(petId);
    }
    @When("I send a GET request to fetch the pet")
    public void i_send_a_get_request_to_fetch_the_pet() {

        CommonSteps.response =
                petClient.getPetById(petId);
    }
    @Then("all pets should have status {string}")
    public void all_pets_should_have_status(String expectedStatus) {

        List<Map<String, Object>> pets =
                CommonSteps.response.jsonPath().getList("$");

        for (Map<String, Object> pet : pets) {

            assertEquals(expectedStatus, pet.get("status"));
        }
    }
    @When("I fetch pets with status {string}")
    public void i_fetch_pets_with_status(String status) {

        CommonSteps.response =
                petClient.findPetsByStatus(status);
    }
}