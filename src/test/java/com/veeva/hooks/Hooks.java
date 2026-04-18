package com.veeva.hooks;

import com.veeva.context.ScenarioContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Hooks {

    // Logger used to print scenario start and end logs
    private static final Logger log = LogManager.getLogger(Hooks.class);

    // ScenarioContext used to store and clear test data between steps
    private final ScenarioContext scenarioContext;

    // Constructor injection - Cucumber provides ScenarioContext automatically
    public Hooks(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    /*Runs before every scenario execution
     Used here to print scenario start log
     */
    @Before
    public void setUp(Scenario scenario) {
        log.info("===== Starting: {} =====", scenario.getName());
    }

    /* Runs after every scenario execution
    Clears stored test data and prints scenario status
    */
    @After
    public void tearDown(Scenario scenario) {
        scenarioContext.clear();
        log.info("===== Finished: {} | Status: {} =====",
                scenario.getName(), scenario.getStatus());
    }
}