package com.dailycodework.demoshops.assignment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

class TestMuJUnitAssignmentTest {

    private static final String BASE_URL = "https://www.testmuai.com/selenium-playground/";
    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(20);

    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource("browserTargets")
    @Timeout(20)
    void scenarioOneRadioButtonDemo(BrowserTarget target) {
        WebDriver driver = createRemoteDriver(target, "Scenario 1 - Radio Buttons Demo");
        try {
            launchPlaygroundAndWait(driver);

            WebElement radioButtonsDemo = waitFor(driver)
                    .until(ExpectedConditions.elementToBeClickable(By.linkText("Radio Buttons Demo")));
            radioButtonsDemo.click();

            WebElement femaleRadio = waitFor(driver)
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[value='Female']")));
            femaleRadio.click();

            WebElement getValueButton = waitFor(driver)
                    .until(ExpectedConditions.elementToBeClickable(By.id("buttoncheck")));
            getValueButton.click();

            WebElement resultText = waitFor(driver)
                    .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("p.radiobutton")));
            Assertions.assertEquals("Radio button 'Female' is checked", resultText.getText().trim());
        } finally {
            driver.quit();
        }
    }

    @ParameterizedTest(name = "[{index}] {0}")
    @MethodSource("browserTargets")
    @Timeout(20)
    void scenarioTwoWindowPopupModal(BrowserTarget target) {
        WebDriver driver = createRemoteDriver(target, "Scenario 2 - Window Popup Modal");
        try {
            launchPlaygroundAndWait(driver);
            String parentWindow = driver.getWindowHandle();

            WebElement popupModal = waitFor(driver)
                    .until(ExpectedConditions.elementToBeClickable(By.linkText("Window Popup Modal")));
            popupModal.click();

            WebElement twitterButton = waitFor(driver)
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'Follow On Twitter')]")));
            twitterButton.click();

            waitFor(driver).until(d -> d.getWindowHandles().size() > 1);
            Set<String> handles = driver.getWindowHandles();

            Assertions.assertTrue(handles.size() > 1, "Expected a new window to open.");
            Assertions.assertTrue(handles.contains(parentWindow), "Original window handle should still exist.");
        } finally {
            // Single command to close all windows.
            driver.quit();
        }
    }

    private static Stream<Arguments> browserTargets() {
        return Stream.of(
                Arguments.of(new BrowserTarget("Chrome + 128.0 + Windows 10", "chrome", "128.0", "Windows 10")),
                Arguments.of(new BrowserTarget("Microsoft Edge + 127.0 + macOS Ventura", "MicrosoftEdge", "127.0", "macOS Ventura")),
                Arguments.of(new BrowserTarget("Firefox + 130.0 + Windows 11", "firefox", "130.0", "Windows 11")),
                Arguments.of(new BrowserTarget("Internet Explorer + 11.0 + Windows 10", "internet explorer", "11.0", "Windows 10"))
        );
    }

    private static WebDriver createRemoteDriver(BrowserTarget target, String testName) {
        Assumptions.assumeTrue(hasGridConfiguration(),
                "Skipping: set TESTMU_GRID_URL or both TESTMU_USERNAME and TESTMU_ACCESS_KEY to run cloud tests.");

        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability("browserName", target.browserName());
        capabilities.setCapability("browserVersion", target.browserVersion());
        capabilities.setCapability("platformName", target.platformName());

        // Generic vendor options for cloud-grid runs, including required logs/recordings artifacts.
        MutableCapabilities cloudOptions = new MutableCapabilities();
        cloudOptions.setCapability("name", testName + " | " + target.displayName());
        cloudOptions.setCapability("build", "JUnit Assignment Build");
        cloudOptions.setCapability("network", true);
        cloudOptions.setCapability("video", true);
        cloudOptions.setCapability("console", true);
        cloudOptions.setCapability("visual", true);
        capabilities.setCapability("testmu:options", cloudOptions);

        return new RemoteWebDriver(resolveGridUrl(), capabilities);
    }

    private static boolean hasGridConfiguration() {
        String explicitGridUrl = System.getenv("TESTMU_GRID_URL");
        if (explicitGridUrl != null && !explicitGridUrl.isBlank()) {
            return true;
        }

        String username = System.getenv("TESTMU_USERNAME");
        String accessKey = System.getenv("TESTMU_ACCESS_KEY");
        return username != null && !username.isBlank() && accessKey != null && !accessKey.isBlank();
    }

    private static URL resolveGridUrl() {
        String explicitGridUrl = System.getenv("TESTMU_GRID_URL");
        if (explicitGridUrl != null && !explicitGridUrl.isBlank()) {
            return toUrl(explicitGridUrl);
        }

        String username = System.getenv("TESTMU_USERNAME");
        String accessKey = System.getenv("TESTMU_ACCESS_KEY");
        if (username != null && !username.isBlank() && accessKey != null && !accessKey.isBlank()) {
            String constructedUrl = "https://" + username + ":" + accessKey + "@hub.testmuai.com/wd/hub";
            return toUrl(constructedUrl);
        }

        throw new IllegalStateException("Set TESTMU_GRID_URL or both TESTMU_USERNAME and TESTMU_ACCESS_KEY before running tests.");
    }

    private static URL toUrl(String value) {
        try {
            return URI.create(value).toURL();
        } catch (MalformedURLException ex) {
            throw new IllegalArgumentException("Invalid grid URL: " + value, ex);
        }
    }

    private static void launchPlaygroundAndWait(WebDriver driver) {
        driver.get(BASE_URL);
        waitFor(driver).until(d -> "complete".equals(((JavascriptExecutor) d).executeScript("return document.readyState")));
        waitFor(driver).until(d -> {
            List<WebElement> domElements = d.findElements(By.cssSelector("body *"));
            return !domElements.isEmpty();
        });
    }

    private static WebDriverWait waitFor(WebDriver driver) {
        return new WebDriverWait(driver, WAIT_TIMEOUT);
    }

    private record BrowserTarget(String displayName, String browserName, String browserVersion, String platformName) {
        @Override
        public String toString() {
            return displayName;
        }
    }
}
