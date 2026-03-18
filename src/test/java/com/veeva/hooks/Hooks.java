package com.veeva.hooks;

import com.veeva.context.ScenarioContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Hooks {

    private static final Logger log = LogManager.getLogger(Hooks.class);
    private final ScenarioContext scenarioContext;

    public Hooks(ScenarioContext scenarioContext) {
        this.scenarioContext = scenarioContext;
    }

    @Before
    public void setUp(Scenario scenario) {
        log.info("===== Starting: {} =====", scenario.getName());
    }

    @After
    public void tearDown(Scenario scenario) {
        scenarioContext.clear();
        log.info("===== Finished: {} | Status: {} =====",
                scenario.getName(), scenario.getStatus());
    }
}
