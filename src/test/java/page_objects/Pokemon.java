package page_objects;

import command_providers.ActOn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.time.Duration;

public class Pokemon {
    private final static By PokemonBanner = By.className("title-banner__title");
    private final static By CharizardLink = By.linkText("Charizard");
    private final static By CollectibleCardBanner = By.className("b-pageheader__text");
    private final static By GradeLink = By.xpath("//*[@id=\"s0-28-9-0-1[0]-0-1-6-9-4[4]-flyout\"]/button/span");
    private final static By GradeYes = By.xpath("//*[@id=\"s0-28-9-0-1[0]-0-1-6-9-4[4]-flyout\"]/div/ul/li[1]/a/span[1]");
    private final static By FinalResult = By.xpath("//*[@id=\"s0-28-9-0-1[0]-0-0-6-4\"]/div[2]/div/div/h2[text()='35,598 Results']");
    private final static By FinalResultCN = By.className("srp-controls__count-heading");

    private static final Logger LOGGER = LogManager.getLogger(Pokemon.class);

    public WebDriver driver;

    public Pokemon(WebDriver driver) {
        this.driver = driver;
    }

    public Pokemon validateBanner() {
        LOGGER.debug("Validating Pokemon Banner");
        //Wait for pg to load & validate banner
        ActOn.wait(driver, PokemonBanner).waitForElementToBeVisible();
        return this;
    }

    public Pokemon scrollToView() {
        LOGGER.debug("Scrolling to Charizard link & clicking");
        ActOn.element(driver, CharizardLink).scrollIntoView().click();
        return this;
    }

    public Pokemon validateCardBanner(){
        LOGGER.debug("Verify/wait for user lands on page");
        ActOn.wait(driver, CollectibleCardBanner).waitForElementToBeVisible();
        return this;
    }

    public Pokemon selectGradeYes() {
        LOGGER.debug("Selecting Yes from Grade dropdown");
        ActOn.element(driver, GradeLink).click();
        //Click on Yes from Grade dropdown
        ActOn.element(driver, GradeYes).click();
        return this;
    }

    public Pokemon waitForPg() {
        LOGGER.debug("Setting a 5sec wait for pg to load");
        //Setting a 5-second time after above navigation to ensure actual UAT experience
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        return this;
    }

    public Pokemon validateTitle() {
        LOGGER.debug("Validating Collectible Card Games page title");
        String expectedTitle = "Collectible Card Games & Accessories for Sale - eBay";
        String actualTitle = ActOn.browser(driver).captureTitle();

        //Verify pg title: Collectible Card Games & Accessories for Sale - eBay
        Assert.assertEquals(expectedTitle,actualTitle);
        return this;
    }

    public Pokemon resultPull() {
        //Get the results total pulled from webpage "36,422 Results" & use split, so we just get the 35,598
        String result = ActOn.element(driver, FinalResultCN).getTextValue().split(" ")[0];
        System.out.println(result);
        //Manually verify pg total from the web page matches the system println.  Should be around 35,598
        return this;
    }

}




