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

    // Logger to print execution details in console / log file
    private static final Logger log = LogManager.getLogger(PetSteps.class);

    // Client used to make Pet API calls
    private final PetClient petClient = new PetClient();

    // ScenarioContext used to store data between steps
    private final ScenarioContext ctx;

    // Constructor Injection → Cucumber automatically provides ScenarioContext
    public PetSteps(ScenarioContext ctx) {
        this.ctx = ctx;
    }

    // Just a configuration / readiness step
    @Given("the petstore API is available")
    public void the_petstore_api_is_available() {
        log.info("Petstore API configured and ready");
    }

    // Step to create a pet with name and status
    @Given("I create a pet with name {string} and status {string}")
    public void i_create_a_pet(String name, String status) {

        // Generate unique pet id using current timestamp
        long id = System.currentTimeMillis();

        // Call create pet API
        Response response = petClient.createPet(id, name, status);

        // Store important values for later steps
        ctx.set("petId", response.jsonPath().getLong("id"));
        ctx.set("petName", name);
        ctx.set("createResponse", response);

        log.info("Created pet id: {}", ctx.getLong("petId"));
    }

    // Step to create a pet with category
    @Given("I create a new pet with name {string} category {string} and status {string}")
    public void i_create_pet_with_category(String name, String category, String status) {

        long id = System.currentTimeMillis();

        // Call API with category information
        Response response = petClient.createPetWithCategory(id, name, status, category);

        // Store values for validation
        ctx.set("petId", response.jsonPath().getLong("id"));
        ctx.set("petName", name);
        ctx.set("categoryName", category);
        ctx.set("createResponse", response);

        log.info("Created pet with category, id: {}", ctx.getLong("petId"));
    }

    // Validate pet creation success
    @Then("the pet should be created successfully")
    public void the_pet_should_be_created_successfully() {

        Response r = (Response) ctx.get("createResponse");

        // Validate status code
        assertEquals("Pet creation should return 200", 200, r.getStatusCode());

        // Validate id present in response
        assertNotNull("Pet ID should not be null", r.jsonPath().get("id"));
    }

    // Validate category in response
    @Then("the pet should be created with the given category")
    public void the_pet_should_be_created_with_category() {

        Response r = (Response) ctx.get("createResponse");

        assertEquals("Pet creation should return 200", 200, r.getStatusCode());

        // Validate category name
        assertEquals("Category should match",
                ctx.getString("categoryName"),
                r.jsonPath().getString("category.name"));
    }

    // Fetch created pet
    @When("I fetch the created pet")
    public void i_fetch_the_created_pet() {

        Response r = petClient.getPetById(ctx.getLong("petId"));

        // Store response for validations
        ctx.set("getResponse", r);
    }

    // Generic GET pet step (works for existing or non-existing id)
    @When("I send a GET request to fetch the pet")
    public void i_send_get_request_to_fetch_the_pet() {

        // If petId exists use it else use nonExistentPetId
        long id = ctx.get("petId") != null
                ? ctx.getLong("petId")
                : ctx.getLong("nonExistentPetId");

        Response r = petClient.getPetById(id);

        // Store response
        ctx.set("getResponse", r);
        ctx.set("lastResponse", r);

        log.info("GET pet by id: {} returned status: {}", id, r.getStatusCode());
    }

    // Validate pet name
    @Then("the pet name should be {string}")
    public void the_pet_name_should_be(String name) {

        Response r = (Response) ctx.get("getResponse");

        assertEquals("Pet name should match",
                name,
                r.jsonPath().getString("name"));
    }

    // Validate pet status
    @Then("the pet status should be {string}")
    public void the_pet_status_should_be(String status) {

        Response r = (Response) ctx.get("getResponse");

        assertEquals("Pet status should match",
                status,
                r.jsonPath().getString("status"));
    }

    // Update pet status
    @When("I update the pet status to {string}")
    public void i_update_the_pet_status(String status) {

        Response r = petClient.updatePet(
                ctx.getLong("petId"),
                ctx.getString("petName"),
                status);

        // Store response
        ctx.set("lastResponse", r);
        ctx.set("updateResponse", r);
    }

    // Validate current status then update
    @When("I update the pet status from {string} to {string}")
    public void i_update_pet_status_from_to(String from, String to) {

        // Fetch current pet details
        Response current = petClient.getPetById(ctx.getLong("petId"));

        // Validate current status before update
        assertEquals("Pet should currently have status " + from,
                from,
                current.jsonPath().getString("status"));

        log.info("Verified current status is '{}', now updating to '{}'", from, to);

        // Perform update
        i_update_the_pet_status(to);
    }

    // Validate updated status
    @Then("the pet update status should be {string}")
    public void the_pet_update_status_should_be(String status) {

        Response r = (Response) ctx.get("updateResponse");

        assertEquals("Updated status should be " + status,
                status,
                r.jsonPath().getString("status"));
    }

    // Delete pet
    @When("I delete the pet")
    public void i_delete_the_pet() {

        Response r = petClient.deletePet(ctx.getLong("petId"));

        ctx.set("lastResponse", r);
        ctx.set("deleteResponse", r);
    }

    // Try fetching deleted pet
    @When("I fetch the deleted pet")
    public void i_fetch_the_deleted_pet() {

        Response r = petClient.getPetById(ctx.getLong("petId"));

        ctx.set("getResponse", r);
        ctx.set("lastResponse", r);
    }

    // Fetch pets list by status
    @When("I fetch pets with status {string}")
    public void i_fetch_pets_with_status(String status) {

        List<Pet> pets = petClient.findPetsByStatusAsList(status);

        // Store list and response
        ctx.set("petsByStatus", pets);
        ctx.set("lastResponse", petClient.findPetsByStatus(status));

        log.info("Found {} pets with status '{}'", pets.size(), status);
    }

    // Validate all pets have expected status
    @Then("all pets should have status {string}")
    public void all_pets_should_have_status(String expectedStatus) {

        @SuppressWarnings("unchecked")
        List<Pet> pets = (List<Pet>) ctx.get("petsByStatus");

        pets.forEach(p ->
                assertEquals("All pets should have status " + expectedStatus,
                        expectedStatus,
                        p.getStatus()));
    }

    // Generic response status validation
    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(int expectedCode) {

        Response r = (Response) ctx.get("lastResponse");

        assertNotNull("Response should not be null", r);

        assertEquals("Response status code should be " + expectedCode,
                expectedCode,
                r.getStatusCode());
    }

    // Validate using Java Streams if created pet exists in sold pets list
    @Then("the created pet ID should be found in the sold pets list using Java streams")
    public void created_pet_found_in_sold_list() {

        long createdId = ctx.getLong("petId");

        @SuppressWarnings("unchecked")
        List<Pet> soldPets = (List<Pet>) ctx.get("petsByStatus");

        // Stream filtering logic
        Optional<Pet> found = soldPets.stream()
                .filter(p -> p.getId() == createdId)
                .findFirst();

        assertTrue("Pet ID " + createdId + " should be in sold pets list via Java Stream",
                found.isPresent());

        log.info("Pet ID {} found in sold list via Stream", createdId);
    }
}