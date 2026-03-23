package com.veeva.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

// This tells JUnit to run tests using Cucumber framework
@RunWith(Cucumber.class)

@CucumberOptions(

        // Path where Feature files are located
        features = "src/test/resources/features",

        // Packages where Step Definitions and Hooks are present
        glue = {"com.veeva.stepdefinitions", "com.veeva.hooks"},

        // Reporting plugins used to generate execution reports
        plugin = {
                "pretty", // prints readable logs in console
                "html:target/cucumber-reports/report.html", // HTML report
                "json:target/cucumber.json", // JSON report
                "junit:target/cucumber-reports/junit-report.xml" // JUnit report
        },

        // Makes console output more clean and readable
        monochrome = true
)

// Runner class used to start execution of all Cucumber scenarios
public class TestRunner {
}