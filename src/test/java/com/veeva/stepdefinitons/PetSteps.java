package com.veeva.stepdefinitions;

import com.veeva.clients.PetClient;
import com.veeva.context.ScenarioContext;
import com.veeva.models.Pet;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class PetSteps {

    private static final Logger log = LogManager.getLogger(PetSteps.class);
    private final PetClient petClient = new PetClient();
    private final ScenarioContext ctx;

    public PetSteps(ScenarioContext ctx) {
        this.ctx = ctx;
    }

    @Given("the petstore API is available")
    public void the_petstore_api_is_available() {
        log.info("Petstore API configured and ready");
    }

    @Given("I create a pet with name {string} and status {string}")
    public void i_create_a_pet(String name, String status) {
        long id = (long)(Math.random() * 100000);
        Response response = petClient.createPet(id, name, status);
        ctx.set("petId", response.jsonPath().getLong("id"));
        ctx.set("petName", name);
        ctx.set("createResponse", response);
        log.info("Created pet id: {}", ctx.getLong("petId"));
    }

    @Given("I create a new pet with name {string} category {string} and status {string}")
    public void i_create_pet_with_category(String name, String category, String status) {
        long id = (long)(Math.random() * 100000);
        Response response = petClient.createPetWithCategory(id, name, status, category);
        ctx.set("petId", response.jsonPath().getLong("id"));
        ctx.set("petName", name);
        ctx.set("categoryName", category);
        ctx.set("createResponse", response);
        log.info("Created pet with category, id: {}", ctx.getLong("petId"));
    }

    @Then("the pet should be created successfully")
    public void the_pet_should_be_created_successfully() {
        Response r = (Response) ctx.get("createResponse");
        assertEquals("Pet creation should return 200", 200, r.getStatusCode());
        assertNotNull("Pet ID should not be null", r.jsonPath().get("id"));
    }

    @Then("the pet should be created with the given category")
    public void the_pet_should_be_created_with_category() {
        Response r = (Response) ctx.get("createResponse");
        assertEquals("Pet creation should return 200", 200, r.getStatusCode());
        assertEquals("Category should match",
                ctx.getString("categoryName"), r.jsonPath().getString("category.name"));
    }

    @When("I fetch the created pet")
    public void i_fetch_the_created_pet() {
        Response r = petClient.getPetById(ctx.getLong("petId"));
        ctx.set("getResponse", r);
    }

    @When("I send a GET request to fetch the pet")
    public void i_send_get_request_to_fetch_the_pet() {
        Response r = petClient.getPetById(ctx.getLong("petId"));
        ctx.set("getResponse", r);
    }

    @Then("the pet name should be {string}")
    public void the_pet_name_should_be(String name) {
        Response r = (Response) ctx.get("getResponse");
        assertEquals("Pet name should match", name, r.jsonPath().getString("name"));
    }

    @Then("the pet status should be {string}")
    public void the_pet_status_should_be(String status) {
        Response r = (Response) ctx.get("getResponse");
        assertEquals("Pet status should match", status, r.jsonPath().getString("status"));
    }

    @When("I update the pet status to {string}")
    public void i_update_the_pet_status(String status) {
        Response r = petClient.updatePet(
                ctx.getLong("petId"),
                ctx.getString("petName"),
                status);
        ctx.set("lastResponse", r);
        ctx.set("updateResponse", r);
    }

    @When("I update the pet status from {string} to {string}")
    public void i_update_pet_status_from_to(String from, String to) {
        i_update_the_pet_status(to);
    }

    @Then("the pet update status should be {string}")
    public void the_pet_update_status_should_be(String status) {
        Response r = (Response) ctx.get("updateResponse");
        assertEquals("Updated status should be " + status,
                status, r.jsonPath().getString("status"));
    }

    @When("I delete the pet")
    public void i_delete_the_pet() {
        Response r = petClient.deletePet(ctx.getLong("petId"));
        ctx.set("lastResponse", r);
        ctx.set("deleteResponse", r);
    }

    @When("I fetch the deleted pet")
    public void i_fetch_the_deleted_pet() {
        Response r = petClient.getPetById(ctx.getLong("petId"));
        ctx.set("getResponse", r);
        ctx.set("lastResponse", r);
    }

    @When("I fetch pets with status {string}")
    public void i_fetch_pets_with_status(String status) {
        List<Pet> pets = petClient.findPetsByStatusAsList(status);
        ctx.set("petsByStatus", pets);
        ctx.set("lastResponse", petClient.findPetsByStatus(status));
        log.info("Found {} pets with status '{}'", pets.size(), status);
    }

    @Then("all pets should have status {string}")
    public void all_pets_should_have_status(String expectedStatus) {
        @SuppressWarnings("unchecked")
        List<Pet> pets = (List<Pet>) ctx.get("petsByStatus");
        pets.forEach(p -> assertEquals(
                "All pets should have status " + expectedStatus,
                expectedStatus, p.getStatus()));
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(int expectedCode) {
        Response r = (Response) ctx.get("lastResponse");
        assertNotNull("Response should not be null", r);
        assertEquals("Response status code should be " + expectedCode,
                expectedCode, r.getStatusCode());
    }

    @Then("the created pet ID should be found in the sold pets list using Java streams")
    public void created_pet_found_in_sold_list() {
        long createdId = ctx.getLong("petId");
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