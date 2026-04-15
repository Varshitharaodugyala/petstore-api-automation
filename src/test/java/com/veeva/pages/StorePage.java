package com.veeva.pages;

import com.veeva.models.Order;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static io.restassured.RestAssured.given;

public class StorePage extends BasePage {
    private static final Logger log = LogManager.getLogger(StorePage.class);

    /**
     * This method mirrors the createPet logic.
     * It takes raw data, builds the Order POJO internally, and sends the POST request.
     */
    public Response createOrder(long id, int petId, int quantity) {
        Order order = new Order();
        order.setId(id);
        order.setPetId(petId);
        order.setQuantity(quantity);
        order.setStatus("placed");
        order.setComplete(true);

        log.info("🏗️ Building and creating order ID: {}", id);

        return given()
                .spec(requestSpec)
                .body(order)
                .post("/store/order");
    }

    // ... getOrder, deleteOrder, and getInventory remain the same ...


    // --- DO NOT CHANGE THE METHODS BELOW ---

    public Response getOrder(String orderId) {
        log.info("Fetching order id: {}", orderId);
        return given().spec(requestSpec)
                .pathParam("orderId", orderId)
                .get("/store/order/{orderId}");
    }

    public Response deleteOrder(String orderId) {
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