Feature: TC4 - Cross-Endpoint Data Consistency

  # Background step ensures API configuration is ready
  Background:
    Given the petstore API is available

  # Scenario Outline used for Data Driven Testing
  # Same logical flow will run for multiple pets & categories
  Scenario Outline: Verify sold pet appears in findByStatus results using Java Streams
    Given I create a new pet with name "<petName>" category "<category>" and status "available"
    Then the pet should be created with the given category
    When I update the pet status from "available" to "sold"
    Then the response status code should be 200
    When I fetch pets with status "sold"
    Then the created pet ID should be found in the sold pets list using Java streams

    # Test Data Table
    Examples:
      | petName           | category          |
      | HighValue-Bulldog | HighValue-Bulldog |
      | RareCat           | Exotic            |
      | GoldenRetriever   | Premium           |