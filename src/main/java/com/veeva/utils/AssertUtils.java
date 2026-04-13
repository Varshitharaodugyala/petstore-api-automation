package com.veeva.utils;

import static org.junit.Assert.*;

public class AssertUtils {

    public static void assertResponseType(int statusCode, String type) {

        switch (type.toLowerCase()) {
            case "successful":
                assertEquals(200, statusCode);
                break;

            case "not found":
                assertEquals(404, statusCode);
                break;

            case "bad request":
                assertEquals(400, statusCode);
                break;

            default:
                fail("Unknown response type: " + type);
        }
    }
}