package com.veeva.stepdefinitions;

import com.veeva.pages.StorePage;
import com.veeva.context.ScenarioContext;
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

    // Step to create a new order,gemerate random ID
    @Given("I create an order")
    public void createorder() {
        int orderId = (int)(Math.random() * 100000);
        Response r = storeClient.createOrder(orderId, 10, 2);
        ctx.set("orderId", (long) orderId);
        ctx.set("lastResponse", r);
        log.info("Created order id: {}", orderId);
    }

    // Step to fetch order details
    @When("I fetch the order")
    public void fetchorder() {
        int orderId = (int) ctx.getLong("orderId");
        Response r = storeClient.getOrder(orderId);
        ctx.set("lastResponse", r);
    }

    // Step to delete the order
    @When("I delete the order")
    public void deleteorder() {
        int orderId = (int) ctx.getLong("orderId");
        Response r = storeClient.deleteOrder(orderId);
        ctx.set("lastResponse", r);
    }
}