Feature: Negative Pet Scenarios

  Scenario: Fetch non-existing pet returns 404
    Given a pet does not exist with id 999999
    When I send a GET request for the non-existing pet
    Then the response status code should be 404

  Scenario: Delete non-existing pet returns 404
    Given a pet does not exist with id 999999
    When I send a DELETE request for the non-existing pet
    Then the response status code should be 404
