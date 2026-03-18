package com.veeva.stepdefinitions;

import com.veeva.clients.UserClient;
import com.veeva.context.ScenarioContext;
import com.veeva.models.User;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.junit.Assert.*;

public class UserSteps {

    private static final Logger log = LogManager.getLogger(UserSteps.class);
    private final UserClient userClient = new UserClient();
    private final ScenarioContext ctx;

    public UserSteps(ScenarioContext ctx) {
        this.ctx = ctx;
    }

    @When("I create a user with username {string} and email {string}")
    public void i_create_user(String username, String email) {
        User user = new User();
        user.setId(12345L);
        user.setUsername(username);
        user.setFirstName("Test");
        user.setLastName("User");
        user.setEmail(email);
        user.setPassword("password123");
        user.setPhone("9999999999");
        user.setUserStatus(1);

        Response r = userClient.createUser(user);
        ctx.set("lastResponse", r);
        ctx.set("userCreateResponse", r);
        log.info("User create response: {}", r.getStatusCode());
    }

    @Then("the user creation response should not return a server error")
    public void user_creation_not_server_error() {
        Response r = (Response) ctx.get("userCreateResponse");
        assertNotEquals("Should not return 500", 500, r.getStatusCode());
    }

    @When("I request a user with username {string}")
    public void i_request_user(String username) {
        Response r = userClient.getUserByUsername(username);
        ctx.set("lastResponse", r);
        ctx.set("getUserResponse", r);
    }

    @Then("the response message should contain {string}")
    public void response_message_should_contain(String expected) {
        Response r = (Response) ctx.get("getUserResponse");
        String message = r.jsonPath().getString("message");
        assertTrue("Message should contain: " + expected,
                message != null && message.contains(expected));
    }

    @When("I login with username {string} and password {string}")
    public void i_login(String username, String password) {
        Response r = userClient.login(username, password);
        ctx.set("lastResponse", r);
        ctx.set("loginResponse", r);
        log.info("Login response: {}", r.getStatusCode());
    }

    @Then("the login response should not contain a valid session token")
    public void login_should_not_have_token() {
        Response r = (Response) ctx.get("loginResponse");
        boolean noToken = r.getStatusCode() != 200
                || !r.getBody().asString().contains("logged in user session");
        assertTrue("Invalid login should not return a session token", noToken);
    }
}


