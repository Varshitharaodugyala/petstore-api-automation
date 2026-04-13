Feature: TC1 - The Lifecycle of a Pet (CRUD and Chaining)

  Background:
    Given the petstore API is available

  # Data Driven Testing — runs for multiple pet names
  Scenario Outline: Create, retrieve, update and delete a pet
    When I create a pet with name "<petName>" and status "available"
    Then the pet should be created successfully
    When I fetch the created pet
    And the pet name should be "<petName>"
    And the pet status should be "available"
    And I update the pet status from "available" to "sold"
    Then the response should be "successful"
    When I delete the pet
    Then the response should be "successful"
    And I fetch the deleted pet
    Then the response should be "not found"

    Examples:
      | petName  |
      | Dog1     |
      | Bulldog  |
      | Whiskers |

  # Negative — fetch non-existing pet with valid and invalid ids
  Scenario Outline: Fetch non-existing pet with invalid id
    Given a pet does not exist with id "<id>"
    When I send a GET request for the non-existing pet
    Then the response should be "not found"

    Examples:
      | id           |
      | 999999       |
      | abc          |
      | 12.5         |
      | 999999999999 |

  # Negative — delete non-existing pet
  Scenario Outline: Delete non-existing pet returns error
    Given a pet does not exist with id "<invalidId>"
    When I send a DELETE request for the non-existing pet
    Then the response should be "not found"

    Examples:
      | invalidId |
      | 999999    |
      | -1        |
      | 0         |
      | 1.5       |
      | abc       |
      | @@@       |

  # Positive — delete existing pet and verify 404
  Scenario: Delete an existing pet successfully
    Given I create a pet with name "doggy" and status "available"
    When I delete the pet
    Then the response should be "successful"
    When I fetch the deleted pet
    Then the response should be "not found"

  # List filtering by status
  Scenario: Verify pets can be filtered by status
    When I fetch pets with status "available"
    Then the response should be "successful"
    And all pets should have status "available"