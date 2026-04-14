package com.veeva.stepdefinitions;

import com.veeva.pages.StorePage;
import com.veeva.context.ScenarioContext;
import com.veeva.utils.AssertUtils;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// ✅ ENSURE THIS IMPORT IS PRESENT
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
        int orderId = (int)(Math.random() * 100000);
        Response r = storeClient.createOrder(orderId, 10, 2);
        ctx.set("orderId", String.valueOf(orderId));
        ctx.set("lastResponse", r);
        log.info("Created order id: {}", orderId);
    }

    @When("I fetch the created order by ID")
    public void fetchOrder() {
        String orderId = (String) ctx.get("orderId");
        Response r = storeClient.getOrder(orderId);
        ctx.set("lastResponse", r);
        log.info("Fetched order id: {} returned status: {}", orderId, r.getStatusCode());
    }

    @When("I delete the created order by ID")
    public void deleteOrder() {
        String orderId = (String) ctx.get("orderId");
        Response r = storeClient.deleteOrder(orderId);
        ctx.set("lastResponse", r);
        log.info("Deleted order id: {} returned status: {}", orderId, r.getStatusCode());
    }

    @Then("the order should be deleted successfully")
    public void verifyOrderDeleted() throws InterruptedException {
        String orderId = (String) ctx.get("orderId");
        int statusCode = 0;

        for (int i = 0; i < 5; i++) {
            Response r = storeClient.getOrder(orderId);
            statusCode = r.getStatusCode();
            if (statusCode == 404 || statusCode == 400) break;
            log.warn("⏳ Order {} still exists, waiting for deletion sync...", orderId);
            Thread.sleep(3000);
        }
        assertTrue("Expected 404 or 400 but got: " + statusCode,
                statusCode == 404 || statusCode == 400);
    }
}