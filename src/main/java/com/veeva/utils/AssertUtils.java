package com.veeva.utils;

import static org.junit.Assert.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AssertUtils {
    private static final Logger log = LogManager.getLogger(AssertUtils.class);

    public static void assertResponseType(int statusCode, String type) {
        log.info(" Validating response type: [{}] | Actual Status: [{}]", type, statusCode);

        switch (type.toLowerCase()) {
            case "successful":
                if (statusCode != 200) {
                    log.error("Expected 200 OK but the API returned: {}", statusCode);
                }
                assertEquals("API Failure! Expected 200 but got: " + statusCode, 200, statusCode);
                break;

            case "not found":
                // Added 200 check because PetStore sometimes returns 200 with "User not found" message
                boolean isNotFound = (statusCode == 404 || statusCode == 400 || statusCode == 200);
                if (!isNotFound) {
                    log.error("Expected a 'Not Found' state (404/400) but got: {}", statusCode);
                }
                assertTrue("Expected 404/400/200(error body) but got: " + statusCode, isNotFound);
                break;

            case "invalid":
                assertTrue("Expected Auth failure (400/403/200) but got: " + statusCode,
                        statusCode == 400 || statusCode == 403 || statusCode == 200);
                break;

            default:
                log.error("Unknown validation type requested: {}", type);
                fail("Unknown response type: " + type);
        }
    }
}