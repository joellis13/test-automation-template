package com.jellisisland.test.automation.template.pages;

import net.serenitybdd.core.pages.PageObject;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import static org.assertj.core.api.Assertions.assertThat;

public class GoogleSearchResultsPage extends PageObject {

    @FindBy(id = "search")
    private WebElement searchResultsContainer;

    @FindBy(css = "#search .g")
    private WebElement firstSearchResult;

    public void verifyResultsContain(String expectedText) {
        waitFor(searchResultsContainer);
        String pageText = getDriver().getPageSource();
        assertThat(pageText).containsIgnoringCase(expectedText);
    }

    public boolean hasResults() {
        return firstSearchResult.isDisplayed();
    }
}