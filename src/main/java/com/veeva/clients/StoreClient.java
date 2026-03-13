package com.veeva.clients;

import io.restassured.response.Response;

public class StoreClient extends BaseClient {

    public Response getInventory() {

        return request()
                .when()
                .get("/store/inventory");
    }

}