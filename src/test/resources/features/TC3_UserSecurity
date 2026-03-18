@regression @tc3 @negative
Feature: TC3 - User Security and Error Handling (Negative Testing)

  Background:
    Given the petstore API is available

  Scenario: Create user with invalid email format
    When I create a user with username "testuser123" and email "invalid_email"
    Then the user creation response should not return a server error

  Scenario: Fetch a non-existent user returns 404
    When I request a user with username "nonExistentUser123"
    Then the response status code should be 404

  Scenario: Login with incorrect credentials
    When I login with username "wrongUser" and password "wrongPass"
    Then the login response should not contain a valid session token