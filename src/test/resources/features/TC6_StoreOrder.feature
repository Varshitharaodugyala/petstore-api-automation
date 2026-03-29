Feature: Store Order

  # Scenario 1 → Verify order creation and retrieval
  Scenario: Place an order successfully
    Given I create an order
    When I fetch the order
    Then the response status code should be 200


  # Scenario 2 → Verify order deletion
  Scenario: Delete an order successfully
    Given I create an order
    When I delete the order
    Then the response status code should be 200