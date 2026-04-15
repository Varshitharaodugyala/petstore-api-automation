package com.veeva.pages;

import com.veeva.models.Order;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static io.restassured.RestAssured.given;

public class StorePage extends BasePage {
    private static final Logger log = LogManager.getLogger(StorePage.class);


     // It takes raw data, builds the Order POJO internally, and sends the POST request.
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
    //get the order details for the given orderid

    public Response getOrder(String orderId) {
        log.info("Fetching order id: {}", orderId);
        return given().spec(requestSpec)
                .pathParam("orderId", orderId)
                .get("/store/order/{orderId}");
    }
    // delete the order for the given order id
    public Response deleteOrder(String orderId) {
        log.info("Deleting order id: {}", orderId);
        return given().spec(requestSpec)
                .pathParam("orderId", orderId)
                .delete("/store/order/{orderId}");
    }
    //getting the store inventory means getting the count by the status
    public Response getInventory() {
        log.info("Fetching store inventory");
        return given().spec(requestSpec).get("/store/inventory");
    }

}