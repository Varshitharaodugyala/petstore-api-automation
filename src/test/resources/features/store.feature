Feature: Store Order

Scenario: Place an order successfully
  Given I create an order
  When I fetch the order
  Then the response status code should be 200

Scenario: Delete an order successfully
  Given I create an order
  When I delete the order
  Then the response status code should be 200