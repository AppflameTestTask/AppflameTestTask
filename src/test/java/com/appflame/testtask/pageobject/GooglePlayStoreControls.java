package com.appflame.testtask.pageobject;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class GooglePlayStoreControls {

    private AppiumDriver driver;
    private WebDriverWait wait;

    // test app name
    private final String testAppName = "speedtest by ookla";

    // Google PlayStore app DOM elements' wrappers
    private By playstoreSearchField = MobileBy.id("com.android.vending:id/search_bar");
    private By playstoreSearchFieldInputReady = MobileBy.className("android.widget.EditText");
    private By playstoreAppPageInstallButton = MobileBy.className("android.widget.Button");
    private By playstoreAppPageTitle = MobileBy.id("com.android.vending:id/title");
    private By appNameInSuggestionsList = MobileBy.AndroidUIAutomator(String.format("%s%s%s%s\")",
            "new UiSelector()",
            ".resourceId(\"com.android.vending:id/suggest_text\")",
            ".text(\"",
            testAppName.toLowerCase()));

    public GooglePlayStoreControls(AppiumDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(this.driver, 180);
    }


    // page elements' wrappers

    private WebElement getPlaystoreSearchField() {
        return wait.until(ExpectedConditions.elementToBeClickable(playstoreSearchField));
    }

    private WebElement getPlaystoreSearchFieldInputReady() {
        return wait.until(ExpectedConditions
                .elementToBeClickable(playstoreSearchFieldInputReady));
    }

    private WebElement getAppNameInSuggestionsList() {
        return wait.until(ExpectedConditions
                .elementToBeClickable(appNameInSuggestionsList));
    }

    private WebElement getPlaystoreAppPageTitle() {
        return wait.until(ExpectedConditions
                .visibilityOfElementLocated(playstoreAppPageTitle));
    }

    private WebElement getPlaystoreAppPageInstallButton() {
        return wait.until(ExpectedConditions
                .visibilityOfElementLocated(playstoreAppPageInstallButton));
    }

    public void installTestApp() {

        getPlaystoreSearchField().click();
        getPlaystoreSearchFieldInputReady().sendKeys(testAppName);
        getAppNameInSuggestionsList().click();
        Assert.assertTrue(getPlaystoreAppPageTitle()
                .getText()
                .toLowerCase()
                .contains(testAppName),
                "*** *** *** *** *** *** *** ***\n The test app's title doesn't match with the one in GooglePlayStore");

        // start installation process of the test app
        getPlaystoreAppPageInstallButton().click();

        System.out.printf("%s %s\n",
                "*** *** *** *** *** *** *** ***",
                "Installation in process ...");

        // test app installation process
        new WebDriverWait(driver, 600)
                .until(ExpectedConditions
                        .textToBePresentInElement(driver
                                .findElement(playstoreAppPageInstallButton), "Open"));

        System.out.printf("%s %s\n",
                "*** *** *** *** *** *** *** ***",
                "... Test application has been successfully installed");
    }
}
