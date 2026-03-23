Feature: Store Order

  # Scenario 1 → Verify order creation and retrieval
  Scenario: Place an order successfully

    # Step 1 → Create a new order using POST /store/order
    Given I create an order

    # Step 2 → Fetch the same order using GET /store/order/{id}
    When I fetch the order

    # Step 3 → Validate order exists and API returned success
    Then the response status code should be 200


  # Scenario 2 → Verify order deletion
  Scenario: Delete an order successfully

    # Step 1 → Create an order first
    Given I create an order

    # Step 2 → Delete order using DELETE /store/order/{id}
    When I delete the order

    # Step 3 → Validate delete API success response
    Then the response status code should be 200