package com.veeva.clients;

import com.veeva.models.User;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.restassured.RestAssured.given;

public class UserClient extends BaseClient {

    private static final Logger log = LogManager.getLogger(UserClient.class);

    public Response createUser(User user) {
        log.info("Creating user: {}", user.getUsername());
        return given().spec(requestSpec).body(user).when().post("/user");
    }

    public Response getUserByUsername(String username) {
        log.info("Fetching user: {}", username);
        return given().spec(requestSpec).when().get("/user/{username}", username);
    }

    public Response login(String username, String password) {
        log.info("Login attempt - username: {}", username);
        return given().spec(requestSpec)
                .queryParam("username", username)
                .queryParam("password", password)
                .when().get("/user/login");
    }
}