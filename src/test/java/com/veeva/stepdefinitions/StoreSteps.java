package com.veeva.stepdefinitions;

import com.veeva.pages.StorePage;
import com.veeva.context.ScenarioContext;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.junit.Assert.*;

public class StoreSteps {

    private static final Logger log = LogManager.getLogger(StoreSteps.class);
    private final StorePage storeClient = new StorePage();
    private final ScenarioContext ctx;

    public StoreSteps(ScenarioContext ctx) {
        this.ctx = ctx;
    }

    @Given("I create an order")
    public void createOrder() {
        long orderId = System.currentTimeMillis() % 100000;

        Response r = storeClient.createOrder(orderId, 10, 2);

        ctx.set("orderId", String.valueOf(orderId));
        ctx.set("lastResponse", r);

        assertEquals("Order creation failed", 200, r.getStatusCode());
        log.info("Created order successfully for ID: {}", orderId);
    }

    @When("I fetch the created order by ID")
    public void fetchOrder() {
        String orderId = (String) ctx.get("orderId");

        Response r = storeClient.getOrder(orderId);

        ctx.set("lastResponse", r);

        assertEquals("Failed to fetch order", 200, r.getStatusCode());
        log.info("Fetched order successfully for ID: {}", orderId);
    }

    @When("I delete the created order by ID")
    public void deleteOrder() {
        String orderId = (String) ctx.get("orderId");

        Response r = storeClient.deleteOrder(orderId);

        ctx.set("lastResponse", r);

        assertEquals("Order deletion failed", 200, r.getStatusCode());
        log.info("Deleted order id: {} returned status: {}", orderId, r.getStatusCode());
    }

    @Then("the order should be deleted successfully")
    public void verifyOrderDeleted() {
        String orderId = (String) ctx.get("orderId");

        Response r = storeClient.getOrder(orderId);
        int statusCode = r.getStatusCode();

        assertTrue("Expected 404 or 400 but got: " + statusCode,
                statusCode == 404 || statusCode == 400);

        log.info("Verified order deletion for ID: {} with status: {}", orderId, statusCode);
    }
}