package com.veeva.clients;

import io.restassured.response.Response;

public class PetClient extends BaseClient {

    public Response getPetById(int petId) {

        return request()
                .pathParam("petId", petId)
                .when()
                .get("/pet/{petId}");
    }

    public Response createPet(int id, String name, String status) {

        String body = "{\n" +
                "\"id\": " + id + ",\n" +
                "\"name\": \"" + name + "\",\n" +
                "\"status\": \"" + status + "\"\n" +
                "}";

        return request()
                .body(body)
                .when()
                .post("/pet");
    }

}