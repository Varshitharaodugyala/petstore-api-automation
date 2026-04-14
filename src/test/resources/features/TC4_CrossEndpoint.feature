
Feature: TC4 - Cross-Endpoint Data Consistency

  Background:
    Given the petstore API is available

  # Cross-endpoint — create pet, update status, verify in sold list
  Scenario Outline: Verify sold pet appears in findByStatus results
    Given I create a new pet with name "<petName>" category "<category>" and status "available"
    Then the pet should be created successfully
    When I update the pet status from "available" to "sold"
    Then the response should be "successful"
    When I fetch pets with status "sold"
    Then the created pet ID should be found in the sold pets list

    Examples:
      | petName           | category          |
      | HighValue-Bulldog | HighValue-Bulldog |
      | RareCat           | Exotic            |
      | GoldenRetriever   | Premium           |

  # Store order — create and retrieve
  Scenario: Place an order successfully
    Given I create an order
    When I fetch the created order by ID
    Then the response should be "successful"

  # Store order — create and delete with verification
  Scenario: Delete an order successfully
    Given I create an order
    When I delete the created order by ID
    Then the order should be deleted successfully