Feature: TC1 - The Lifecycle of a Pet (CRUD and Chaining)

  # Background runs before every Scenario
  # Used for common precondition setup
  Background:
    Given the petstore API is available

  # Scenario Outline is used for Data Driven Testing
  # Same scenario will run multiple times with different pet names
  Scenario Outline: Create, retrieve, update and delete a pet
    Given I create a pet with name "<petName>" and status "available"
    Then the pet should be created successfully
    When I fetch the created pet
    Then the pet name should be "<petName>"
    And the pet status should be "available"
    When I update the pet status to "sold"
    Then the response status code should be 200
    When I delete the pet
    Then the response status code should be 200
    When I fetch the deleted pet
    Then the response status code should be 404

    # Test Data Table
    Examples:
      | petName  |
      | Dog1     |
      | Bulldog  |
      | Whiskers |

  # Negative Testing Scenario

  Scenario Outline: Fetch non-existing pet with invalid id
    Given a pet does not exist with id "<id>"
    When I send a GET request for the non-existing pet
    Then the response status code should be 404

    Examples:
      | id        |
      | 999999    |
      | abc       |
      | 12.5      |
      | 999999999999 |
  # List Filtering Scenario
  Scenario: Verify pets can be filtered by status
    When I fetch pets with status "available"
    Then the response status code should be 200
    And all pets should have status "available"