package com.veeva.clients;

import com.veeva.config.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PetClient {

    public Response getPetById(int petId) {

        RestAssured.baseURI = ConfigManager.getBaseUrl();

        Response response = given()
                .pathParam("petId", petId)
                .when()
                .get("/pet/{petId}");

        return response;

    }
}