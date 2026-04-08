Feature: Negative Pet Scenarios
#deleting the non existed pet and then checking whether the staus code is 404 or not.
  Scenario Outline: Delete non-existing pet returns error

    Given a pet does not exist with id "<invalidId>"
    When I send a DELETE request for the non-existing pet
    Then the response should be "not found"

    Examples:
      | invalidId |
      | 999999    |
      | -1        |
      | 0         |
      | 1.5       |
      | abc       |
      | @@@       |