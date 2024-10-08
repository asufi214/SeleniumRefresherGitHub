package ebayhome;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SearchFunction {
    WebDriver driver;

    @BeforeMethod
    public void openBrowser(){

        //Opening Chrome
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        //Navigating to ebay homepage
        driver.get("https://www.ebay.com/");
        //Maximizing the Chrome tab for full visibility
        driver.manage().window().maximize();

        //Clear the Search Bar (should already be clear, but just in case)
        //   driver.findElement(By.id("gh-ac")).clear();
 //       driver.findElement(By.id("gh-ac")).sendKeys("iPhone 15 Pro Max");
 //       driver.findElement(By.id("gh-btn")).click();
    }


    @Test
    public void verifyButton(){
        driver.findElement(By.id("gh-ac")).sendKeys("iPhone 15 Pro Max");
        driver.findElement(By.id("gh-btn")).click();

        String expectedTitle = "iPhone 15 Pro Max for sale | eBay";
        String actualTitle = driver.getTitle();

        //Validate homepage has changed to reflect the searched item "iPhone 15 Pro Max for sale | eBay"
        Assert.assertEquals(expectedTitle,actualTitle);
    }

    @AfterMethod
    public void closeBrowser(){
        //Close the browser
        driver.quit();
    }
}
