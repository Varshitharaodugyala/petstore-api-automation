package com.veeva.utils;

import com.github.javafaker.Faker;

public class TestDataGenerator {

    private static Faker faker = new Faker();

    public static String getPetName() {
        return faker.animal().name();
    }

    public static String getStatus() {
        return "available";
    }
}