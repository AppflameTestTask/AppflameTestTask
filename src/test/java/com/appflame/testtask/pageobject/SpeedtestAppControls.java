package com.appflame.testtask.pageobject;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SpeedtestAppControls {

    private AppiumDriver driver;
    private WebDriverWait wait;

    // test app DOM elements' wrappers
    private By nextButton = MobileBy
            .className("android.widget.Button"); // the value is common for 'Next' and 'Continue' buttons
    private By goButton = MobileBy
            .id("org.zwanoo.android.speedtest:id/go_button");
    private By downloadStats = MobileBy
            .xpath("//android.widget.FrameLayout[@content-desc='DOWNLOAD']//android.widget.TextView[@resource-id='org.zwanoo.android.speedtest:id/txt_test_result_value']");
    private By uploadStats = MobileBy
            .xpath("//android.widget.FrameLayout[@content-desc='UPLOAD']//android.widget.TextView[@resource-id='org.zwanoo.android.speedtest:id/txt_test_result_value']");
    private By pingStats = MobileBy
            .xpath("//android.view.ViewGroup[@content-desc='Ping']//android.widget.TextView[@resource-id='org.zwanoo.android.speedtest:id/txt_test_result_value']");
    private By jitterStats = MobileBy
            .xpath("//android.view.ViewGroup[@content-desc='Jitter']//android.widget.TextView[@resource-id='org.zwanoo.android.speedtest:id/txt_test_result_value']");
    private By lossStats = MobileBy
            .xpath("//android.view.ViewGroup[@resource-id='org.zwanoo.android.speedtest:id/test_result_item_packet_loss']//android.widget.TextView[@resource-id='org.zwanoo.android.speedtest:id/txt_test_result_value']");

    public SpeedtestAppControls(AppiumDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(this.driver, 180);
    }


    // test app page elements' wrappers

    private WebElement getNextButton() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(nextButton));
    }

    private WebElement getGoButton() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(goButton));
    }

    public WebElement getDownloadStats() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(downloadStats));
    }

    public WebElement getUploadStats() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(uploadStats));
    }

    public WebElement getPingStats() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(pingStats));
    }

    public WebElement getJitterStats() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(jitterStats));
    }

    public WebElement getLossStats() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(lossStats));
    }

    // skips the test app's initial setup and kicks off network test process
    public void runNetworkScan() {

        // wait and click the test app's 'Next' button
        wait.until(ExpectedConditions.elementToBeClickable(getNextButton()))
                .click();

        // wait and click the test app's 'Continue' button
        wait.until(ExpectedConditions.elementToBeClickable(getNextButton()))
                .click();

        // wait for the 2nd permissions popup and dismiss it
        wait.until(ExpectedConditions.alertIsPresent()).dismiss();

        // wait for the 2nd permissions popup and dismiss it
        wait.until(ExpectedConditions.alertIsPresent()).dismiss();

        // wait and click any of the test app's 'Go' button
        wait.until(ExpectedConditions.elementToBeClickable(getGoButton()))
                .click();
    }

}
