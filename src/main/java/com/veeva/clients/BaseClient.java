package com.veeva.clients;

import com.veeva.config.ConfigManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class BaseClient {

    protected static final RequestSpecification requestSpec =
            new RequestSpecBuilder()
                    .setBaseUri(ConfigManager.getBaseUrl())
                    .setContentType(ContentType.JSON)
                    .addFilter(new RequestLoggingFilter())
                    .addFilter(new ResponseLoggingFilter())
                    .build();
}
