package com.veeva.stepdefinitions;

import com.veeva.pages.StorePage;
import com.veeva.context.ScenarioContext;
import com.veeva.utils.AssertUtils;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StoreSteps {

    private static final Logger log = LogManager.getLogger(StoreSteps.class);
    private final StorePage storeClient = new StorePage();
    private final ScenarioContext ctx;

    public StoreSteps(ScenarioContext ctx) {
        this.ctx = ctx;
    }

    // Step to create a new order, generate random ID
    @Given("I create an order")
    public void createOrder() {
        int orderId = (int)(Math.random() * 100000);
        Response r = storeClient.createOrder(orderId, 10, 2);
        ctx.set("orderId", String.valueOf(orderId));
        ctx.set("lastResponse", r);
        log.info("Created order id: {}", orderId);
    }

    // Step to fetch order details — accepts any type of id (valid or invalid)
    @When("I fetch the created order by ID")
    public void fetchOrder() {
        String orderId = (String) ctx.get("orderId");
        Response r = storeClient.getOrder(orderId);
        ctx.set("lastResponse", r);
        log.info("Fetched order id: {} returned status: {}", orderId, r.getStatusCode());
    }

    // Step to delete the order — accepts any type of id (valid or invalid)
    @When("I delete the created order by ID")
    public void deleteOrder() {
        String orderId = (String) ctx.get("orderId");
        Response r = storeClient.deleteOrder(orderId);
        ctx.set("lastResponse", r);
        log.info("Deleted order id: {} returned status: {}", orderId, r.getStatusCode());
    }

    // Verifies deletion by re-fetching and expecting 404
    @Then("the order should be deleted successfully")
    public void verifyOrderDeleted() {
        String orderId = (String) ctx.get("orderId");
        Response r = storeClient.getOrder(orderId);
        AssertUtils.assertResponseType(r.getStatusCode(), "not found");
        log.info("Order id: {} confirmed deleted, received 404", orderId);
    }
}