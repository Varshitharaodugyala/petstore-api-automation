Feature: TC2 - Inventory Analysis (Dynamic and Reusable)

  Background:
    Given the petstore API is available

  # Validate findByStatus returns non-empty list for each status
  Scenario Outline: Validate findByStatus API
    When I fetch pets with status as "<status>"
    Then the response should be successful and "<status>" list should not be empty

    Examples:
      | status    |
      | available |
      | sold      |
      | pending   |

  # Cross-endpoint — compare inventory count vs findByStatus count
  Scenario Outline: Compare inventory and pet counts
    Given the petstore API is available
    When I fetch the store inventory
    And I fetch pets with status "<status>"
    Then the inventory response should be successful
    And the pet counts for "<status>" should approximately match

    Examples:
      | status    |
      | available |
      | pending   |
      | sold      |