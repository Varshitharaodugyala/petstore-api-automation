package com.veeva.clients;

import io.restassured.response.Response;

public class StoreClient extends BaseClient {

    public Response createOrder(int orderId, int petId, int quantity) {

        String body = "{\n" +
                "\"id\": " + orderId + ",\n" +
                "\"petId\": " + petId + ",\n" +
                "\"quantity\": " + quantity + ",\n" +
                "\"status\": \"placed\",\n" +
                "\"complete\": true\n" +
                "}";

        return request()
                .body(body)
                .post("/store/order");
    }

    public Response getOrder(int orderId) {

        return request()
                .pathParam("orderId", orderId)
                .get("/store/order/{orderId}");
    }

    public Response deleteOrder(int orderId) {

        return request()
                .pathParam("orderId", orderId)
                .delete("/store/order/{orderId}");
    }

    public Response getInventory() {

        return request()
                .get("/store/inventory");
    }
}