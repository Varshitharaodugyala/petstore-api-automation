// Handles all User related API operations like create user, fetch user and login
// Extends BaseClient to reuse common request configuration

package com.veeva.pages;
import com.veeva.models.User;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.restassured.RestAssured.given;

public class UserPage extends BasePage {
    private static final Logger log = LogManager.getLogger(UserPage.class);

    public Response createUser(User user) {
        log.info("Creating user: {}", user.getUsername());

        return given().spec(requestSpec).body(user).when().post("/user");
    }
    /*
     * This method sends GET request to fetch user details using username.
     * Username is passed as path parameter which replaces {username}
     * in the endpoint URL.
     */
    public Response getUserByUsername(String username) {
        log.info("Fetching user: {}", username);
        return given().spec(requestSpec).when().get("/user/{username}", username);
    }
    /*
     * This method performs login operation using username and password.
     * Login API requires credentials to be sent as query parameters.
     * Example: /user/login?username=john&password=1234
     */

    public Response login(String username, String password) {
        log.info("Login attempt - username: {}", username);
        return given().spec(requestSpec)
                .queryParam("username", username)
                .queryParam("password", password)
                .when().get("/user/login");
    }
}