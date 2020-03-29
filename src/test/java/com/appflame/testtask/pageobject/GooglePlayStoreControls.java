package com.appflame.testtask.pageobject;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GooglePlayStoreControls {

    private AppiumDriver driver;
    private WebDriverWait wait;

    // test app name
    private final String testAppName = "speedtest by ookla";

    // Google PlayStore app DOM elements' selectors
    private String playstoreSearchField = "com.android.vending:id/search_bar";
    private String playstoreSearchFieldInputReady = "android.widget.EditText";
    private String playstoreAppPageInstallButton = "android.widget.Button";
    private String appNameInSuggestionsList = String.format("%s%s%s%s\")",
            "new UiSelector()",
            ".resourceId(\"com.android.vending:id/suggest_text\")",
            ".text(\"",
            testAppName.toLowerCase());

    public GooglePlayStoreControls(AppiumDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(this.driver, 180);
    }

    private WebElement getPlaystoreSearchField() {
        return wait.until(ExpectedConditions.elementToBeClickable(MobileBy.id(playstoreSearchField)));
    }

    private WebElement getPlaystoreSearchFieldInputReady() {
        return wait.until(ExpectedConditions
                .elementToBeClickable(MobileBy.className(playstoreSearchFieldInputReady)));
    }

    private WebElement getAppNameInSuggestionsList() {
        return wait.until(ExpectedConditions
                .elementToBeClickable(MobileBy.AndroidUIAutomator(appNameInSuggestionsList)));
    }

    private WebElement getPlaystoreAppPageInstallButton() {
        return wait.until(ExpectedConditions
                .visibilityOfElementLocated(MobileBy.className(playstoreAppPageInstallButton)));
    }

    public void installTestApp() {

        getPlaystoreSearchField().click();
        getPlaystoreSearchFieldInputReady().sendKeys(testAppName);
        getAppNameInSuggestionsList().click();

        // start installation process of the test app
        getPlaystoreAppPageInstallButton().click();

        System.out.printf("%s %s\n",
                "*** *** *** *** *** *** *** ***",
                "Installation in process ...");

        // test app installation process
        new WebDriverWait(driver, 600)
                .until(ExpectedConditions
                        .textToBePresentInElement(driver
                                .findElement(MobileBy
                                        .className(playstoreAppPageInstallButton)), "Open"));

        System.out.printf("%s %s\n",
                "*** *** *** *** *** *** *** ***",
                "... Test application has been successfully installed");
    }
}
