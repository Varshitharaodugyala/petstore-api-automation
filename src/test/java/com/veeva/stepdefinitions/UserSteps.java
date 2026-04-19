package com.veeva.stepdefinitions;

import com.veeva.pages.UserPage;
import com.veeva.context.ScenarioContext;
import com.veeva.utils.AssertUtils;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserSteps {

    private static final Logger log = LogManager.getLogger(UserSteps.class);
    private final UserPage userClient = new UserPage();
    private final ScenarioContext ctx;

    public UserSteps(ScenarioContext ctx) {
        this.ctx = ctx;
    }

    // Create user
    @When("I create a user with username {string} and email {string}")
    public void createUser(String username, String email) {
        long id = System.currentTimeMillis() % 1000000;
        log.info("Creating user → username: {}, email: {}", username, email);
        Response r = userClient.createUser(id, username, email);
        ctx.set("lastResponse", r);
        ctx.set("userCreateResponse", r);

        // store correct credentials
        ctx.set("username", username);
        ctx.set("password", "password123");
        //  AssertUtils.assertResponseType(r.getStatusCode(), "successful");
        log.info("User created successfully with status: {}", r.getStatusCode());
    }

    // Validate no server error
    @Then("the user creation response should not return a server error")
    public void serverError() {
        Response r = (Response) ctx.get("userCreateResponse");
        int statusCode = r.getStatusCode();
        log.info("User creation status: {}", statusCode);
        AssertUtils.assertResponseType(r.getStatusCode(), "no server error");
    }

    // Fetch user
    @When("I request a user with username {string}")
    public void requestUser(String username) {
        Response r = userClient.getUserByUsername(username);
        ctx.set("lastResponse", r);
        ctx.set("getUserResponse", r);
        log.info("Fetching user '{}' → status: {}", username, r.getStatusCode());
    }

    // Validate message
    @Then("the response message should contain {string}")
    public void responseMessage(String expected) {
        Response r = (Response) ctx.get("getUserResponse");
        String message = r.jsonPath().getString("message");
        log.info("Response message: {}", message);
        if (message == null || !message.contains(expected)) {
            throw new AssertionError(
                    "Expected message to contain: " + expected + " but got: " + message);
        }
        log.info("Validated message contains '{}'", expected);
    }

    // Validate not found
    @Then("the user response should be not found")
    public void userResponseShouldBeNotFound() {
        Response r = (Response) ctx.get("getUserResponse");
        AssertUtils.assertResponseType(r.getStatusCode(), "not found");
        log.info("Verified response is Not Found");
    }

    // Login
    @When("I login with username {string} and password {string}")
    public void login(String username, String password) {
        Response r = userClient.login(username, password);
        ctx.set("lastResponse", r);
        ctx.set("loginResponse", r);

        // store entered credentials
        ctx.set("loginUsername", username);
        ctx.set("loginPassword", password);
        log.info("Login attempt → user: {}, status: {}", username, r.getStatusCode());
    }

   //Validate login (valid + invalid in one method)
    @Then("the login should be {string}")
    public void validateLogin(String expectedResult) {
        Response response = (Response) ctx.get("lastResponse");
        String correctUser = ctx.getString("username");
        String correctPass = ctx.getString("password");
        String enteredUser = ctx.getString("loginUsername");
        String enteredPass = ctx.getString("loginPassword");
        boolean isValid =
                correctUser.equals(enteredUser) &&
                        correctPass.equals(enteredPass);
        if (expectedResult.equalsIgnoreCase("successful")) {
            if (!isValid) {
                throw new AssertionError("Expected valid login but credentials did not match");
            }
            AssertUtils.assertResponseType(response.getStatusCode(), "successful");

        } else if (expectedResult.equalsIgnoreCase("invalid")) {
            if (isValid) {
                throw new AssertionError("Expected invalid login but credentials matched");
            }
            AssertUtils.assertResponseType(response.getStatusCode(), "invalid");

        } else {
            throw new IllegalArgumentException("Unknown expected result: " + expectedResult);
        }
        log.info("Login validation completed → expected: {}", expectedResult);
    }
}
