package com.jellisisland.test.automation.template.pages;

import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.annotations.DefaultUrl;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@DefaultUrl("https://www.google.com")
public class GoogleHomePage extends PageObject {

    @FindBy(name = "q")
    private WebElement searchBox;

    @FindBy(name = "btnK")
    private WebElement searchButton;

    @FindBy(css = "input[value='Google Search']")
    private WebElement googleSearchButton;

    public void enterSearchTerm(String searchTerm) {
        searchBox.clear();
        searchBox.sendKeys(searchTerm);
    }

    public void clickSearchButton() {
        // Wait for the search button to be clickable
        waitFor(searchBox);
        searchBox.submit();
    }
}