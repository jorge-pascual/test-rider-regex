package com.example.demo.glue;

import com.example.demo.TestService;
import com.github.database.rider.core.api.dataset.CompareOperation;
import com.github.database.rider.core.api.dataset.DataSetExecutor;
import com.github.database.rider.core.configuration.DataSetConfig;
import com.github.database.rider.core.configuration.ExpectedDataSetConfig;
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

import static com.github.database.rider.core.dsl.RiderDSL.withConnection;

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
            try {
                withConnection(connection)
                        .withDataSetConfig(new DataSetConfig("database/result.yml"))
                        .expectDataSet(new ExpectedDataSetConfig().compareOperation(CompareOperation.CONTAINS));
            } catch (DatabaseUnitException e) {
                e.printStackTrace();
            }
        });
    }
}
