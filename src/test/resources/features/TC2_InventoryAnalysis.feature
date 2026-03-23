Feature: TC2 - Inventory Analysis (Complex Data Parsing)

  # This scenario validates consistency between two different APIs
  # 1) /store/inventory → returns count of pets by status
  # 2) /pet/findByStatus → returns actual list of pets

  Scenario: Validate available pets count matches between inventory and findByStatus

    # Step 1 → Fetch inventory map (status → count)
    When I fetch the store inventory

    # Validate API responded correctly
    Then the inventory response status should be 200

    # Validate inventory contains available pets count
    And the inventory should contain available pets count

    # Step 2 → Fetch actual list of available pets
    When I fetch pets with status available

    # Validate response success
    Then the pets response status should be 200

    # Ensure list is not empty
    And the available pets list should not be empty

    # Step 3 → Compare counts from both APIs
    Then the available pet counts should approximately match