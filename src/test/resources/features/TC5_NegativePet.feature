Feature: Negative Pet Scenarios

  # Scenario 1 → Try fetching a pet that does not exist
  Scenario: Fetch non-existing pet returns 404

    # Ensure the pet with given id is not present in system
    Given a pet does not exist with id 999999

    # Send GET request for invalid pet id
    When I send a GET request for the non-existing pet

    # Validate API returns proper error status
    Then the response status code should be 404


  # Scenario 2 → Try deleting a pet that does not exist
  Scenario: Delete non-existing pet returns 404

    # Ensure pet is not present
    Given a pet does not exist with id 999999

    # Send DELETE request for invalid pet id
    When I send a DELETE request for the non-existing pet

    # Validate API handles safely and returns error code
    Then the response status code should be 404