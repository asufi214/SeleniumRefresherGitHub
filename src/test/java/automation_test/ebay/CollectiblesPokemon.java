package automation_test.ebay;

import command_providers.ActOn;
import command_providers.WaitFor;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class CollectiblesPokemon {

    private final static By CollectiblesLink = By.linkText("Collectibles");
    private final static By PokemonLink = By.linkText("Pokemon");
    private final static By PokemonBanner = By.className("title-banner__title");
    private final static By CharizardLink = By.linkText("Charizard");
    private final static By CollectibleCardBanner = By.className("b-pageheader__text");
    private final static By GradeLink = By.xpath("//*[@id=\"s0-28-9-0-1[0]-0-1-6-9-4[4]-flyout\"]/button");
    private final static By GradeYes = By.xpath("//*[@id=\"s0-28-9-0-1[0]-0-1-6-9-4[4]-flyout\"]/div/ul/li[1]/a/span[1]");
    private final static By FinalResult = By.xpath("//*[@id=\"s0-28-9-0-1[0]-0-0-6-4\"]/div[2]/div/div/h2[text()='36,422 Results']");
    private final static By FinalResultCN = By.className("srp-controls__count-heading");

    WebDriver driver;


    @BeforeMethod
    public void OpenBrowser() {
        //Select Chrome
        WebDriverManager.chromedriver().setup();
        //Double-Click on Chrome
        driver = new ChromeDriver();
        //Navigate to ebay.com
        ActOn.browser(driver).openBrowser("https://www.ebay.com/");
    }

    public void SiteNavigation() {
        //Hover mouse to Collectables so dropdown option Pokémon is available
        ActOn.element(driver, CollectiblesLink).mouseHover();
        //Click on Pokemon
        ActOn.element(driver, PokemonLink).click();
        //Wait for pg to load
        ActOn.wait(driver, PokemonBanner).waitForElementToBeVisible();
        //Scroll to Charizard link & click
        ActOn.element(driver, CharizardLink).scrollIntoView().click();
        //Verify/wait for user lands on page
        ActOn.wait(driver, CollectibleCardBanner).waitForElementToBeVisible();
        //Select Grade dropdown
        ActOn.element(driver, GradeLink).click();
        //Click on Yes from Grade dropdown
        ActOn.element(driver, GradeYes).click();
    }

    @Test
    public void VerifyResults() {
        //ensure above navigation on site is pulled 1st
        SiteNavigation();
        //Manual 5 sec wait to ensure actual UAT loading
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        //Use String to set up expected vs actual title value
        String expectedTitle = "Collectible Card Games & Accessories for Sale - eBay";
        String actualPgTitle = ActOn.browser(driver).captureTitle();
        //Verify pg title: Collectible Card Games & Accessories for Sale - eBay
        Assert.assertEquals(expectedTitle, actualPgTitle);

        //Get the results total pulled from webpage "36,422 Results" & use split, so we just get the 36,422
        String result = ActOn.element(driver, FinalResultCN).getTextValue().split(" ")[0];
        System.out.println(result);
        //Manually verify pg total from the web page matches the system println.  Should be around 36,422

    }

    @AfterMethod
    public void closeBrowser() {
        //Close Chrome
        ActOn.browser(driver).closeBrowser();
    }
}
