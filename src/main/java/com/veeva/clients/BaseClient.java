package com.veeva.clients;
import io.restassured.http.ContentType;
import com.veeva.config.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BaseClient {

    protected RequestSpecification request() {
        return RestAssured
                .given()
                .baseUri(ConfigManager.getBaseUrl())
                .contentType(ContentType.JSON)
                .log().all();   // logs request details
    }
}