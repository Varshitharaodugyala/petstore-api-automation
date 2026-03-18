package com.veeva.clients;

import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.restassured.RestAssured.given;

public class StoreClient extends BaseClient {

    private static final Logger log = LogManager.getLogger(StoreClient.class);

    public Response createOrder(int orderId, int petId, int quantity) {
        String body = "{\n" +
                "\"id\": " + orderId + ",\n" +
                "\"petId\": " + petId + ",\n" +
                "\"quantity\": " + quantity + ",\n" +
                "\"status\": \"placed\",\n" +
                "\"complete\": true\n" +
                "}";
        log.info("Creating order id: {}", orderId);
        return given().spec(requestSpec).body(body).post("/store/order");
    }

    public Response getOrder(int orderId) {
        log.info("Fetching order id: {}", orderId);
        return given().spec(requestSpec)
                .pathParam("orderId", orderId)
                .get("/store/order/{orderId}");
    }

    public Response deleteOrder(int orderId) {
        log.info("Deleting order id: {}", orderId);
        return given().spec(requestSpec)
                .pathParam("orderId", orderId)
                .delete("/store/order/{orderId}");
    }

    public Response getInventory() {
        log.info("Fetching store inventory");
        return given().spec(requestSpec).get("/store/inventory");
    }

    public int getAvailableCount() {
        Integer count = getInventory().jsonPath().get("available");
        log.info("Available count from inventory: {}", count);
        return count != null ? count : 0;
    }
}
