Feature: TC3 - User Security and Error Handling (Negative Testing)

  # Background runs before every scenario
  # Ensures API base configuration / readiness
  Background:
    Given the petstore API is available

  # Scenario 1 → Invalid Input Testing
  Scenario: Create user with invalid email format

    # Try creating user with wrong email format
    When I create a user with username "testuser123" and email "invalid_email"

    # Validate API does NOT crash (no 500 error)
    Then the user creation response should not return a server error


  # Scenario 2 → Resource Not Found Testing
  Scenario: Fetch a non-existent user returns 404

    # Try fetching user which does not exist
    When I request a user with username "nonExistentUser123"

    # Validate proper error status returned
    Then the response status code should be 404


  # Scenario 3 → Authentication Negative Testing
  Scenario: Login with incorrect credentials does not cause server error

    # Try login with invalid username/password
    When I login with username "wrongUser" and password "wrongPass"

    # Validate API handled failure gracefully
    Then the login response should not contain a valid session token