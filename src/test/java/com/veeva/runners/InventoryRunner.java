package com.veeva.runners;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/inventory.feature",
        glue = "com.veeva.stepdefinitions",
        plugin = {"pretty", "html:target/cucumber-reports/inventory.html"},
        monochrome = true
)
public class InventoryRunner {
}