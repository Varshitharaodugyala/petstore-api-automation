/*StoreClient handles all API operations related to Store module.
  It contains reusable methods for creating orders, fetching order details,
   deleting orders and getting inventory information from Petstore API.
  This class extends BaseClient so that all common request configurations
  like base URL, content type and logging are automatically applied.

 */
package com.veeva.pages;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.restassured.RestAssured.given;

public class StorePage extends BasePage {
    private static final Logger log = LogManager.getLogger(StorePage.class);
    // This method sends POST request to create a new order in the store.
    // It builds the request body with order details and uses the common requestSpec
    // from BaseClient to send the request.
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
    // This method fetches order details using orderId as path parameter.
    // It sends GET request to retrieve specific order information.
    public Response getOrder(int orderId) {
        log.info("Fetching order id: {}", orderId);
        return given().spec(requestSpec)
                .pathParam("orderId", orderId)
                .get("/store/order/{orderId}");
    }
    // This method deletes an existing order using orderId.
    // It sends DELETE request to remove the order from the system.
    public Response deleteOrder(int orderId) {
        log.info("Deleting order id: {}", orderId);
        return given().spec(requestSpec)
                .pathParam("orderId", orderId)
                .delete("/store/order/{orderId}");
    }

    // This method retrieves store inventory details like available,
    // pending and sold pet counts.
    public Response getInventory() {
        log.info("Fetching store inventory");
        return given().spec(requestSpec).get("/store/inventory");
    }
    // This helper method calls inventory API and extracts the count
    // of pets with 'available' status from the response.
    // If value is not present, it safely returns 0 to avoid null issues.
    public int getAvailableCount() {
        Integer count = getInventory().jsonPath().get("available");
        log.info("Available count from inventory: {}", count);
        return count != null ? count : 0;
    }
}
