@regression @tc2
Feature: TC2 - Inventory Analysis (Complex Data Parsing)

  Scenario: Validate available pets count matches between inventory and findByStatus
    When I fetch the store inventory
    Then the inventory response status should be 200
    And the inventory should contain available pets count
    When I fetch pets with status available
    Then the pets response status should be 200
    And the available pets list should not be empty
    Then the available pet counts should approximately match