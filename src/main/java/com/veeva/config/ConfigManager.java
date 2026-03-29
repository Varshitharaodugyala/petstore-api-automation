
// This class is used to read configuration values from config.properties file
// like Base URL and make them available to the framework
package com.veeva.config;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    // Properties object to store key-value pairs from properties file
    private static Properties properties = new Properties();
    // Static block runs once when class is loaded and reads the config file

    static {
        try {
            // Opening config.properties file from resources folder
            FileInputStream file = new FileInputStream("src/test/resources/config.properties");
            // Loading all properties into Properties object
            properties.load(file);
        } catch (IOException e) {
            // Prints error if file is not found or cannot be read
            e.printStackTrace();
        }
    }
    // Method to return Base URL value from properties file
    public static String getBaseUrl() {
        String url = properties.getProperty("baseUrl");
        System.out.println("🔥 Base URL = " + url);
        return url;
    }
}