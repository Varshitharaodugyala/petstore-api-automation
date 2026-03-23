Feature: TC1 - The Lifecycle of a Pet (CRUD and Chaining)

  # Background runs before every Scenario
  # Used for common precondition setup
  Background:
    Given the petstore API is available

  # Scenario Outline is used for Data Driven Testing
  # Same scenario will run multiple times with different pet names
  Scenario Outline: Create, retrieve, update and delete a pet

    # Step → Create pet
    Given I create a pet with name "<petName>" and status "available"

    # Validate creation success
    Then the pet should be created successfully

    # Fetch created pet
    When I fetch the created pet

    # Validate pet details
    Then the pet name should be "<petName>"
    And the pet status should be "available"

    # Update pet status
    When I update the pet status to "sold"

    # Validate update response
    Then the response status code should be 200

    # Delete pet
    When I delete the pet

    # Validate delete response
    Then the response status code should be 200

    # Try fetching deleted pet
    When I fetch the deleted pet

    # Validate pet not found
    Then the response status code should be 404

    # Test Data Table
    Examples:
      | petName  |
      | Dog1     |
      | Bulldog  |
      | Whiskers |

  # Negative Testing Scenario
  Scenario: Fetch pet with invalid ID returns 404

    # Ensure pet does not exist
    Given a pet does not exist with id 999999999

    # Try fetching invalid pet
    When I send a GET request to fetch the pet

    # Validate error response
    Then the response status code should be 404

  # List Filtering Scenario
  Scenario: Verify pets can be filtered by status

    # Fetch pets with specific status
    When I fetch pets with status "available"

    # Validate API response success
    Then the response status code should be 200

    # Validate all returned pets have expected status
    And all pets should have status "available"