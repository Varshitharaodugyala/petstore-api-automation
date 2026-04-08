Feature: TC2 - Inventory Analysis (Complex Data Parsing)

  # Scenario 1 → Validate /store/inventory API
  Scenario: Validate inventory API returns available pet count
    When I fetch the store inventory
    Then the inventory response should be successful
    And the inventory should contain available pets count


  # Scenario 2 → Validate /pet/findByStatus API
  Scenario: Validate findByStatus API returns available pets list
    When I fetch pets with status available
    Then the pets response should be successful
    And the available pets list should not be empty


  # Scenario 3 → Compare both APIs (optional but recommended)
  Scenario: Validate available pet counts match between APIs
    When I fetch the store inventory
    And I fetch pets with status available
    Then the available pet counts should approximately match