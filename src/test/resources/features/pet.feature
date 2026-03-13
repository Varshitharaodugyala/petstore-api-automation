Feature: Pet Lifecycle

Scenario: Create and fetch a pet
  Given I create a pet with name "Dog1" and status "available"
  When I fetch the created pet
  Then the pet status should be "available"


Scenario: Fetch pet with invalid ID
  Given a pet does not exist with id 999999999
  When I send a GET request to fetch the pet
  Then the response status code should be 404


Scenario: Pet lifecycle
  Given I create a pet with name "Bulldog" and status "available"

  When I fetch the created pet
  Then the pet name should be "Bulldog"
  And the pet status should be "available"

  When I update the pet status to "sold"
  Then the response status code should be 200

  When I delete the pet
  Then the response status code should be 200

  When I fetch the deleted pet
  Then the response status code should be 404
Scenario: Verify pets can be filtered by status
  When I fetch pets with status "available"
  Then the response status code should be 200
  And all pets should have status "available"

