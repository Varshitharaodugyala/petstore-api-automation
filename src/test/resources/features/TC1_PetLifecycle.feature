Feature: TC1 - The Lifecycle of a Pet (CRUD and Chaining)

  Background:
    Given the petstore API is available

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

    Examples:
      | petName  |
      | Dog1     |
      | Bulldog  |
      | Whiskers |

  Scenario: Fetch pet with invalid ID returns 404
    Given a pet does not exist with id 999999999
    When I send a GET request to fetch the pet
    Then the response status code should be 404

  Scenario: Verify pets can be filtered by status
    When I fetch pets with status "available"
    Then the response status code should be 200
    And all pets should have status "available"
