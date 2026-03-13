package com.veeva.stepdefinitions;

import com.veeva.clients.PetClient;
import io.cucumber.java.en.*;

public class NegativePetSteps {

    private PetClient petClient = new PetClient();
    private int petId;

    @Given("a pet does not exist with id {int}")
    public void set_non_existing_pet_id(Integer id) {

        petId = id;
        petClient.deletePet(petId);
    }

    @When("I send a GET request for the non-existing pet")
    public void i_send_a_get_request_for_non_existing_pet() {

        CommonSteps.response =
                petClient.getPetById(petId);
    }

    @When("I send a DELETE request for the non-existing pet")
    public void i_send_a_delete_request_for_non_existing_pet() {

        CommonSteps.response =
                petClient.deletePet(petId);
    }

    @Given("I try to create a pet with name {string} and status {string}")
    public void i_try_to_create_a_pet_with_invalid_data(String name, String status) {

        CommonSteps.response =
                petClient.createPet((int)(Math.random()*100000), name, status);
    }
}