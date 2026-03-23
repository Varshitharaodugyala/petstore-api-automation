package com.veeva.stepdefinitions;

import com.veeva.clients.StoreClient;
import com.veeva.context.ScenarioContext;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StoreSteps {

    // Logger to print execution details
    private static final Logger log = LogManager.getLogger(StoreSteps.class);

    // Client used to call Store / Order APIs
    private final StoreClient storeClient = new StoreClient();

    // ScenarioContext used to store data between steps
    private final ScenarioContext ctx;

    // Constructor Injection → Cucumber automatically provides ScenarioContext
    public StoreSteps(ScenarioContext ctx) {
        this.ctx = ctx;
    }

    // Step to create a new order
    @Given("I create an order")
    public void i_create_an_order() {

        // Generate random order id
        int orderId = (int)(Math.random() * 100000);

        // Call create order API (petId = 10, quantity = 2)
        Response r = storeClient.createOrder(orderId, 10, 2);

        // Store order id and response for later steps
        ctx.set("orderId", (long) orderId);
        ctx.set("lastResponse", r);

        // Log created order id
        log.info("Created order id: {}", orderId);
    }

    // Step to fetch order details
    @When("I fetch the order")
    public void i_fetch_the_order() {

        // Get stored order id from ScenarioContext
        int orderId = (int) ctx.getLong("orderId");

        // Call GET /store/order/{id}
        Response r = storeClient.getOrder(orderId);

        // Store response for validation
        ctx.set("lastResponse", r);
    }

    // Step to delete the order
    @When("I delete the order")
    public void i_delete_the_order() {

        // Get stored order id
        int orderId = (int) ctx.getLong("orderId");

        // Call DELETE /store/order/{id}
        Response r = storeClient.deleteOrder(orderId);

        // Store response
        ctx.set("lastResponse", r);
    }
}