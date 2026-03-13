Feature: Inventory Analysis

Scenario: Verify available pet count

  When I fetch the store inventory
  And I fetch pets with status available
  Then the available pet counts should match