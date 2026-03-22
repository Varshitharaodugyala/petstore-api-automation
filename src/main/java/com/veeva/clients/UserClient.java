// Handles all User related API operations like create user, fetch user and login
// Extends BaseClient to reuse common request configuration

package com.veeva.clients;
import com.veeva.models.User;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.restassured.RestAssured.given;

public class UserClient extends BaseClient {
    // Logger to print execution details for debugging
    private static final Logger log = LogManager.getLogger(UserClient.class);
    /*
     * This method sends POST request to create a new user.
     *
     * Instead of manually creating JSON string, we pass User POJO object.
     * REST Assured automatically converts this Java object into JSON
     * before sending the request (Serialization).
     */
    public Response createUser(User user) {
        log.info("Creating user: {}", user.getUsername());
        /*
         * given() → start building API request
         * spec(requestSpec) → apply common request configuration from BaseClient
         * body(user) → attach request body (User object converted to JSON)
                * post("/user") → send POST request to create user
         */
        return given().spec(requestSpec).body(user).when().post("/user");
    }
    /*
     * This method sends GET request to fetch user details using username.
     *
     * Username is passed as path parameter which replaces {username}
     * in the endpoint URL.
     */
    public Response getUserByUsername(String username) {
        log.info("Fetching user: {}", username);
        return given().spec(requestSpec).when().get("/user/{username}", username);
    }
    /*
     * This method performs login operation using username and password.
     *
     * Login API requires credentials to be sent as query parameters.
     * Example:
     * /user/login?username=john&password=1234
     */

    public Response login(String username, String password) {
        log.info("Login attempt - username: {}", username);
        return given().spec(requestSpec)
                .queryParam("username", username)
                .queryParam("password", password)
                .when().get("/user/login");
    }
}