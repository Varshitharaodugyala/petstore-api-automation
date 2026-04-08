Feature: Store Order

  # Scenario 1 → Verify order creation and retrieval
  Scenario: Place an order successfully
    Given I create an order
    When I fetch the created order by ID
    Then the response should be "successful"


  # Scenario 2 → Verify order deletion
  Scenario: Delete an order successfully
    Given I create an order
    When I delete the created order by ID
    Then the response should be "successful"