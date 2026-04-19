package com.veeva.utils;

import static org.junit.Assert.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AssertUtils {
    private static final Logger log = LogManager.getLogger(AssertUtils.class);
    public static boolean isSuccessful(int statusCode) {
        return statusCode == 200;
    }
    // checking the responses
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
                log.info("Validating NOT FOUND scenario | Actual Status: [{}]", statusCode);

                if (statusCode == 404 || statusCode == 400) {
                    assertTrue(true);
                }
                else if (statusCode == 200) {
                    // 200 is NOT acceptable for this test
                    log.error("Received 200 OK for NOT FOUND scenario — this is incorrect");
                    fail("Expected 404/400 but got 200 OK");
                }
                else {
                    log.error("Unexpected status code for NOT FOUND: {}", statusCode);
                    fail("Expected 404/400 but got: " + statusCode);
                }
                break;

            case "invalid":
                assertTrue("Expected Auth failure (400/403/200) but got: " + statusCode,
                        statusCode == 400 || statusCode == 403 || statusCode == 200);
                break;

            case "no server error":
                if (statusCode >= 500) {
                    throw new AssertionError("Expected no server error but got: " + statusCode);
                }
                break;

            default:
                log.error("Unknown validation type requested: {}", type);
                fail("Unknown response type: " + type);
        }
    }
}