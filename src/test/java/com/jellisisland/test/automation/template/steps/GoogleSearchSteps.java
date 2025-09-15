package com.jellisisland.test.automation.template.steps;

import net.serenitybdd.annotations.Step;
import com.jellisisland.test.automation.template.pages.GoogleHomePage;
import com.jellisisland.test.automation.template.pages.GoogleSearchResultsPage;

public class GoogleSearchSteps {

    GoogleHomePage googleHomePage;
    GoogleSearchResultsPage googleSearchResultsPage;

    @Step("Open Google homepage")
    public void openGoogleHomepage() {
        googleHomePage.open();
    }

    @Step("Search for '{0}'")
    public void searchFor(String searchTerm) {
        googleHomePage.enterSearchTerm(searchTerm);
        googleHomePage.clickSearchButton();
    }

    @Step("Verify search results contain '{0}'")
    public void verifySearchResults(String expectedText) {
        googleSearchResultsPage.verifyResultsContain(expectedText);
    }
}