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

public class SearchFunction {

    private final By SearchBarField = By.id("gh-ac");
    private final By SearchButton = By.id("gh-btn");
    private final By HomePage = By.id("gh-la");

    WebDriver driver;

    @BeforeMethod
    public void openBrowser(){

        //Opening Chrome
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        //Navigating to ebay homepage
        ActOn.browser(driver).openBrowser("https://www.ebay.com/");

    }


    @Test
    public void verifyButton(){
        ActOn.element(driver, SearchBarField).setValue("iPhone 15 Pro Max");
        ActOn.element(driver, SearchButton).click();

        String expectedTitleI = "iPhone 15 Pro Max for sale | eBay";
        String actualTitleI = ActOn.browser(driver).captureTitle();

        //Validate homepage has changed to reflect the searched item "iPhone 15 Pro Max for sale | eBay"
        Assert.assertEquals(expectedTitleI,actualTitleI);

        //Click on Ebay home icon to Return to home
        ActOn.element(driver, HomePage).click();

        ActOn.element(driver, SearchBarField).setValue("g7 Lumix");
        ActOn.element(driver, SearchButton).click();

        String expectedTitleL = "G7 Lumix for sale | eBay";
        String actualTitleL = ActOn.browser(driver).captureTitle();

        //Validate homepage has changed to reflect the searched item "iPhone 15 Pro Max for sale | eBay"
        Assert.assertEquals(expectedTitleL,actualTitleL);

        //Click on Ebay home icon to Return to home
        ActOn.element(driver, HomePage).click();

        String expectedTitleH = "Electronics, Cars, Fashion, Collectibles & More | eBay";
        String actualTitleH = ActOn.browser(driver).captureTitle();

        //Validate homepage has changed to reflect the searched item "iPhone 15 Pro Max for sale | eBay"
        Assert.assertEquals(expectedTitleH,actualTitleH);

    }

    @AfterMethod
    public void closeBrowser(){
        //Close the browser
        ActOn.browser(driver).closeBrowser();
    }
}
