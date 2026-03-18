package com.veeva.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.veeva.stepdefinitions", "com.veeva.hooks"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/report.html",
                "json:target/cucumber.json",
                "junit:target/cucumber-reports/junit-report.xml"
        },
        monochrome = true
)
public class TestRunner {
}