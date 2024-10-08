package ebayhome;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestNgWebDriver {
    WebDriver driver;

    @BeforeMethod
    public void OpenBrowser(){
        //open Chrome browser
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        //maximize the browser tab
        driver.manage().window().maximize();

        //Go to homepage
        driver.get("https://ebay.com/");
    }


    @Test
    public void VerifyHomePageTitle(){
        //Originally I put expected title as "ebay", but after inspecting on the homepage I realised it was
        //titled differently, so changed to "Electronics, Cars, Fashion, Collectibles & More | eBay"
        String expectedTitle = "Electronics, Cars, Fashion, Collectibles & More | eBay";
        String actualTitle = driver.getTitle();

        //Validate homepage with "ebay" title
        Assert.assertEquals(actualTitle, expectedTitle);
    }

    @AfterMethod

    public void CloseBrowser(){
        //Close browser
        driver.quit();
    }
}

//TC01 - Validate the homepage title
//1. Open Chrome browser
//2. Navigate to ebay.com
//3. Validate homepage title
//4. Close the browse