package automation_test.ebay;

import command_providers.ActOn;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page_objects.NavigationBar;

import java.time.Duration;

public class CollectiblesPokemonPgObj {

    private final static By CollectiblesLink = By.linkText("Collectibles");
    private final static By PokemonLink = By.linkText("Pokemon");
    private final static By PokemonBanner = By.className("title-banner__title");
    private final static By CharizardLink = By.linkText("Charizard");
    private final static By CollectibleCardBanner = By.className("b-pageheader__text");
    private final static By GradeLink = By.xpath("//*[@id=\"s0-28-9-0-1[0]-0-1-6-9-4[4]-flyout\"]/button]");
    private final static By GradeYes = By.xpath("//*[@id=\"s0-28-9-0-1[0]-0-1-6-9-4[4]-flyout\"]/div/ul/li[1]/a/span[1]");
    private final static By FinalResult = By.xpath("//*[@id=\"s0-28-9-0-1[0]-0-0-6-4\"]/div[2]/div/div/h2[text()='35,598 Results']");
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


    @Test
    public void VerifyResults() {
        new NavigationBar(driver)
                .mouseHoverToCollectibles()
                .navigateToPokemon()
                .waitForPg()
                .validateBanner()
                .scrollToView()
                .waitForPg()
                .validateCardBanner()
                .selectGradeYes()
                .waitForPg()
                .validateTitle()
                .resultPull();
    }

    @AfterMethod
    public void closeBrowser() {
        //Close Chrome
        ActOn.browser(driver).closeBrowser();
    }
}
