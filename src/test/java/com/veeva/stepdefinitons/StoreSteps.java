package com.veeva.stepdefinitions;

import com.veeva.clients.StoreClient;
import io.cucumber.java.en.*;
import io.restassured.response.Response;

public class StoreSteps {

    private StoreClient storeClient = new StoreClient();
    private int orderId;

    @Given("I create an order")
    public void i_create_an_order() {

        orderId = (int) (Math.random() * 100000);

        CommonSteps.response =
                storeClient.createOrder(orderId, 10, 2);
    }

    @When("I fetch the order")
    public void i_fetch_the_order() {

        CommonSteps.response =
                storeClient.getOrder(orderId);
    }

    @When("I delete the order")
    public void i_delete_the_order() {

        CommonSteps.response =
                storeClient.deleteOrder(orderId);
    }
}