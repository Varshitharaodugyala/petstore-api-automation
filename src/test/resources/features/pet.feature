Feature: Pet API Testing

Scenario: Create and fetch pet

  Given I create a new pet
  When I fetch the created pet
  Then the response status code should be 200

Scenario: Fetch pet with invalid ID
  Given a pet exists with id 999999
  When I send a GET request to fetch the pet
  Then the response status code should be 404