package com.appflame.testtask;

import com.appflame.testtask.pageobject.GooglePlayStoreControls;
import com.appflame.testtask.pageobject.SpeedtestAppControls;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;

public class SpeedtestAppSimpleFlowTest {

    final private String testAppPackage = "org.zwanoo.android.speedtest";
    final private String playstorePackage = "com.android.vending";

    private AppiumDriver driver = null;
    private GooglePlayStoreControls playStore = null;
    private SpeedtestAppControls testApp = null;

    @BeforeClass
    public void initialSetUp() throws MalformedURLException {
        driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), getPlaystoreCapabilities());
        if (!driver.isAppInstalled(testAppPackage)) {
            driver.activateApp(playstorePackage);
            playStore = new GooglePlayStoreControls(driver);
            playStore.installTestApp();
            driver.terminateApp(playstorePackage);
        }

        System.out.printf("%s %s\n",
                "*** *** *** *** *** *** *** ***",
                "Test application has already been installed on a device");

        if (driver != null) {
            driver.quit();
        }
    }

    @BeforeMethod
    public void beforeEachTestSetup() throws MalformedURLException {
        driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), getInstalledAppCapabilities());
        testApp = new SpeedtestAppControls(driver);
        testApp.runNetworkScan();
    }

    @AfterMethod
    public void afterEachTestCleanUp() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void TestAppShowsDownloadStats() {
        // wait until the stats value is present then obtain resulting number and print it out into console
        String downloadStats = testApp.getDownloadStats().getText();
        System.out.printf("Download value: %s\n", downloadStats);
    }

    @Test
    public void TestAppShowsUploadStats() {
        // wait until the stats value is present then obtain resulting number and print it out into console
        String uploadStats = testApp.getUploadStats().getText();
        System.out.printf("Upload value: %s\n", uploadStats);
    }

    @Test
    public void TestAppShowsPingStats() {
        // wait until the stats value is present then obtain resulting number and print it out into console
        String pingStats = testApp.getPingStats().getText();
        System.out.printf("Ping value: %s\n", pingStats);
    }

    @Test
    public void TestAppShowsJitterStats() {
        // wait until the stats value is present then obtain resulting number and print it out into console
        String jitterStats = testApp.getJitterStats().getText();
        System.out.printf("Jitter value: %s\n", jitterStats);
    }

    @Test
    public void TestAppShowsLossStats() {
        // wait until the stats value is present then obtain resulting number and print it out into console
        String lossStats = testApp.getLossStats().getText();
        System.out.printf("Loss value: %s\n", lossStats);
    }

    private DesiredCapabilities getPlaystoreCapabilities() {

        // a set of capabilities required for the test app installation from the Google Play Store
        DesiredCapabilities caps = DesiredCapabilities.android();

        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("useNewWDA", false);
        caps.setCapability("deviceReadyTimeout", 600);
        caps.setCapability("newCommandTimeOut", 300);

        caps.setCapability("deviceName", "Android Emulator");
        caps.setCapability("avd", "Nexus_5X_API_28");
        caps.setCapability("platformVersion", "9.0");
        caps.setCapability("platformName", "Android");
        caps.setCapability("deviceOrientation", "portrait");
        caps.setCapability("browserName", "");
        caps.setCapability("adbExecTimeout", 60000);
        caps.setCapability("androidInstallTimeout", 600000);

        return caps;
    }

    private DesiredCapabilities getInstalledAppCapabilities() {

        // a set of capabilities required for the test app's start up
        // and optional installation process in case the app hasn't been installed yet

        // .apk file is used as a workaround for an adb permissions related issue in a local environment
        // which makes impossible start up of the installed app by means of appPackage' and 'appActivity' capabilities
        // some details are here: https://github.com/appium/appium/blob/master/docs/en/writing-running-appium/android/activity-startup.md#possible-problems-and-solutions

        final String testAppActivity = "com.ookla.mobile4.screens.welcome.WelcomeActivity";
        @SuppressWarnings("ConstantConditions")
        String testAppPath = getClass()
                .getClassLoader()
                .getResource("Speedtest.apk")
                .getPath();

        DesiredCapabilities caps = DesiredCapabilities.android();

        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("useNewWDA", false);
        caps.setCapability("fullReset", false);
        caps.setCapability("noReset", false);
        caps.setCapability("deviceReadyTimeout", 300);
        caps.setCapability("newCommandTimeOut", 300);

        caps.setCapability("deviceName", "Android Emulator");
        caps.setCapability("avd", "Nexus_5X_API_28"); // Pixel_3a_API_29 // Nexus_5X_API_28
        caps.setCapability("platformVersion", "9.0"); // 10.0
        caps.setCapability("platformName", "Android");
        caps.setCapability("deviceOrientation", "portrait");
        caps.setCapability("app", testAppPath);
        caps.setCapability("adbExecTimeout", 60000);
        caps.setCapability("androidInstallTimeout", 180000);

        caps.setCapability("browserName", "");
        caps.setCapability("appWaitActivity", testAppActivity);

        return caps;
    }
}
