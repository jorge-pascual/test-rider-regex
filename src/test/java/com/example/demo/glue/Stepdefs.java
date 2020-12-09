package com.example.demo.glue;

import com.example.demo.TestService;
import com.github.database.rider.core.api.dataset.DataSetExecutor;
import com.github.database.rider.core.configuration.DataSetConfig;
import com.github.database.rider.core.connection.ConnectionHolderImpl;
import com.github.database.rider.core.dataset.DataSetExecutorImpl;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.dbunit.DatabaseUnitException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class Stepdefs {

    @Autowired
    private TestService testService;

    @PersistenceContext
    private EntityManager em;

    @Given("A few records")
    public void aFewRecords() {
        testService.saveItems();
    }

    @When("we test them against a regex dataset")
    public void weTestThemAgainstARegexDataset() {
    }

    @Then("we expect it works")
    @Transactional
    public void weExpectItWorks() {

        Session hibernateSession = em.unwrap(Session.class);
        hibernateSession.doWork(connection -> {
            DataSetExecutor dbunitExecutor = DataSetExecutorImpl.instance(new ConnectionHolderImpl(connection));
            try {
                dbunitExecutor
                    .compareCurrentDataSetWith(
                        new DataSetConfig("database/result.yml"),
                        new String[] {},
                        null,
                            new String[] {}
                );
            } catch (DatabaseUnitException e) {
                e.printStackTrace();
            }
        });
    }
}
