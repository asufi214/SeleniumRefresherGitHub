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
import page_objects.Home;
import page_objects.NavigationBar;

public class SearchFunctionPgObj {

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
        new Home(driver)
                .backToHome()
                .typeiPhone("iPhone 15 Pro Max")
                .clickOnSearch()
                .waitForPg()
                .validateIphone()
                .typeLumixG7("g7 Lumix")
                .clickOnSearch()
                .waitForPg()
                .validateLumix()
                .backToHome()
                .waitForPg()
                .validateHome();
    }

    @AfterMethod
    public void closeBrowser(){
        //Close the browser
        ActOn.browser(driver).closeBrowser();
    }
}
