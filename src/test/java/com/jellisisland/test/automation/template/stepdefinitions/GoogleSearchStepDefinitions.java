package com.jellisisland.test.automation.template.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.annotations.Steps;
import com.jellisisland.test.automation.template.steps.GoogleSearchSteps;

public class GoogleSearchStepDefinitions {

    @Steps
    GoogleSearchSteps googleSearchSteps;

    @Given("I am on the Google homepage")
    public void i_am_on_the_google_homepage() {
        googleSearchSteps.openGoogleHomepage();
    }

    @When("I search for {string}")
    public void i_search_for(String searchTerm) {
        googleSearchSteps.searchFor(searchTerm);
    }

    @Then("I should see search results containing {string}")
    public void i_should_see_search_results_containing(String expectedText) {
        googleSearchSteps.verifySearchResults(expectedText);
    }
}