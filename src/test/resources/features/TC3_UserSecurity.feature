Feature: TC3 - User Security and Error Handling (Negative Testing)

  Background:
    Given the petstore API is available

  Scenario Outline: Create user with invalid email format

    When I create a user with username "<username>" and email "<email>"
    Then the user creation response should not return a server error

    Examples:
      | username   | email            |
      | testuser1  | invalid_email    |
      | testuser2  | abc              |
      | testuser3  | user@            |
      | testuser4  | @gmail.com       |


  Scenario Outline: Fetch a non-existent user returns 404


    When I request a user with username "<username>"
    Then the user response status should be 404
    And the response message should contain "User not found"

    Examples:
      | username             |
      | nonExistentUser123   |
      | randomUser999        |
      | unknown_user         |


  Scenario Outline: Login with incorrect credentials


    When I login with username "<username>" and password "<password>"
    Then the login response should not contain a valid session token

    Examples:
      | username    | password   |
      | wrongUser   | wrongPass  |
      | invalidUser | 123456     |
      | testUser    | wrongPwd   |
