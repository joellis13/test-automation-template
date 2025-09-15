package com.jellisisland.test.automation.template.pages;

import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class GoogleSearchResultsPage extends PageObject {

    @FindBy(id = "search")
    private WebElement searchResultsContainer;

    @FindBy(css = "#search .g")
    private WebElement firstSearchResult;

    public void shouldContainText(String expectedText) {
        waitFor(searchResultsContainer);
        searchResultsContainer.shouldContainText(expectedText);
    }

    public boolean hasResults() {
        return firstSearchResult.isDisplayed();
    }
}