Feature: TC3 - User Security and Error Handling (Negative Testing)

  Background:
    Given the petstore API is available

  # Create user with invalid email formats
  Scenario Outline: Create user with invalid email format
    When I create a user with username "<username>" and email "<email>"
    Then the user creation response should not return a server error

    Examples:
      | username  | email         |
      | testUser1 | invalid_email |
      | testUser2 | abc           |
      | testUser3 | user@         |
      | testUser4 | @gmail.com    |

  # Positive login — valid credentials
  Scenario: Login with correct credentials
    When I create a user with username "validUser1" and email "valid@mail.com"
    And I login with username "validUser1" and password "password123"
    Then the login should be "successful"

  # Negative login — incorrect credentials
  Scenario Outline: Login with incorrect credentials
    When I create a user with username "validUser2" and email "valid2@mail.com"
    And I login with username "<username>" and password "<password>"
    Then the login should be "invalid"

    Examples:
      | username    | password  |
      | wrongUser   | wrongPass |
      | validUser2  | wrongPass |
      | invalidUser | 123456    |

  # Fetch non-existent user — expect 404 and message validation
  Scenario Outline: Fetch a non-existent user returns 404
    When I request a user with username "<username>"
    And the user response should be not found
    Then the response message should contain "User not found"

    Examples:
      | username           |
      | nonExistentUser123 |
      | randomUser999      |
      | unknown_user       |