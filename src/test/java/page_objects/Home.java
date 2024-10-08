package page_objects;

import command_providers.ActOn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.time.Duration;

public class Home {
    private final By SearchBarField = By.id("gh-ac");
    private final By SearchButton = By.id("gh-btn");
    private final By HomePage = By.id("gh-la");

    private static final Logger LOGGER = LogManager.getLogger(Home.class);

    public WebDriver driver;

    public Home(WebDriver driver) {
        this.driver = driver;
    }

    public Home typeiPhone(String value) {
        LOGGER.debug("Typing the search: " + value);
        ActOn.element(driver, SearchBarField).setValue(value);
        return this;
    }

    public Home clickOnSearch() {
        LOGGER.debug("Clicking on the Search button");
        ActOn.element(driver, SearchButton).click();
        return this;
    }

    public Home typeLumixG7(String value) {
        LOGGER.debug("Typing the search: " + value);
        ActOn.element(driver, SearchBarField).setValue(value);
        return this;
        }

    public Home backToHome() {
        LOGGER.debug("Returning to home");
        ActOn.element(driver, HomePage).click();
        return this;
    }

    public Home validateIphone() {
        LOGGER.debug("Validating iphone page title");
        String expectedTitleI = "iPhone 15 Pro Max for sale | eBay";
        String actualTitleI = ActOn.browser(driver).captureTitle();

        //Validate homepage has changed to reflect the searched item "iPhone 15 Pro Max for sale | eBay"
        Assert.assertEquals(expectedTitleI,actualTitleI);
        return this;
    }

    public Home validateLumix() {
        LOGGER.debug("Validating Lumix page title");
        String expectedTitleL = "G7 Lumix for sale | eBay";
        String actualTitleL = ActOn.browser(driver).captureTitle();

        //Validate homepage has changed to reflect the searched item "iPhone 15 Pro Max for sale | eBay"
        Assert.assertEquals(expectedTitleL,actualTitleL);
        return this;
    }

    public Home validateHome() {
        LOGGER.debug("Validating home page title");
        String expectedTitleH = "Electronics, Cars, Fashion, Collectibles & More | eBay";
        String actualTitleH = ActOn.browser(driver).captureTitle();

        //Validate homepage has changed to reflect the searched item "iPhone 15 Pro Max for sale | eBay"
        Assert.assertEquals(expectedTitleH,actualTitleH);
        return this;
    }

    public Home waitForPg() {
        LOGGER.debug("Setting a 5sec wait for pg to load");
        //Setting a 3-second time after above navigation to ensure actual UAT experience
       driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        return this;
    }

    }



