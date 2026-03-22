package com.veeva.utils;

import com.github.javafaker.Faker;

// Utility class used to generate dynamic test data for API tests
// Helps avoid using hardcoded values in tests
// Makes tests more realistic and reusable
public class TestDataGenerator {

    // Faker object used to generate random realistic data
    private static Faker faker = new Faker();

    // Generates random animal name to be used as Pet name in API request
    public static String getPetName() {
        return faker.animal().name();
    }

    // Returns default status value for Pet
    // Can be extended later to return random status (available / pending / sold)
    public static String getStatus() {
        return "available";
    }
}