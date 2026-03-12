package com.veeva.clients;

import io.restassured.response.Response;

public class PetClient extends BaseClient {

    public Response getPetById(int petId) {

        return request()
                .pathParam("petId", petId)
                .when()
                .get("/pet/{petId}");
    }
}