package com.veeva.clients;

import com.veeva.config.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BaseClient {

    protected RequestSpecification request() {

        RestAssured.baseURI = ConfigManager.getBaseUrl();

        return given()
                .header("Content-Type", "application/json");
    }
}