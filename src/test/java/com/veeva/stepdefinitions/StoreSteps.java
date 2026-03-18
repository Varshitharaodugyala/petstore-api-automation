package com.veeva.stepdefinitions;

import com.veeva.clients.StoreClient;
import com.veeva.context.ScenarioContext;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StoreSteps {

    private static final Logger log = LogManager.getLogger(StoreSteps.class);
    private final StoreClient storeClient = new StoreClient();
    private final ScenarioContext ctx;

    public StoreSteps(ScenarioContext ctx) {
        this.ctx = ctx;
    }

    @Given("I create an order")
    public void i_create_an_order() {
        int orderId = (int)(Math.random() * 100000);
        Response r = storeClient.createOrder(orderId, 10, 2);
        ctx.set("orderId", (long) orderId);
        ctx.set("lastResponse", r);
        log.info("Created order id: {}", orderId);
    }

    @When("I fetch the order")
    public void i_fetch_the_order() {
        int orderId = (int) ctx.getLong("orderId");
        Response r = storeClient.getOrder(orderId);
        ctx.set("lastResponse", r);
    }

    @When("I delete the order")
    public void i_delete_the_order() {
        int orderId = (int) ctx.getLong("orderId");
        Response r = storeClient.deleteOrder(orderId);
        ctx.set("lastResponse", r);
    }
}