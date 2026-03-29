Feature: Negative Pet Scenarios

  Scenario Outline: Delete non-existing pet returns error

    Given a pet does not exist with id "<invalidId>"
    When I send a DELETE request for the non-existing pet
    Then the response status code should be 404

    Examples:
      | invalidId |
      | 999999    |
      | -1        |
      | 0         |
      | 1.5       |
      | abc       |
      | @@@       |