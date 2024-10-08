package excluded_test;

import command_providers.ActOn;
import command_providers.AssertThat;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class RefurbishedElectronics {

    private By ElectronicsTab = By.linkText("Electronics");
    private By RefurbishedLink = By.linkText("eBay Refurbished");
    private By RefurbishedIcon = By.xpath("/html/body/div[3]/div[2]/section/div[1]/div[2]/h1[text()='eBay Refurbished']");
    private By IphoneLink = By.xpath("//*[@id=\"mainContent\"]/section[5]/div[2]/span[1]/a/div[2][text()='iPhones']/..");
    private By NetworkDropDown = By.xpath("//*[@id=\"s0-28_1-9-0-1[0]-0-0-6-8-4[4]-flyout\"]/button/span[text()='Network']");
    private By IphoneVersion = By.xpath("//*[@id=\"s0-28_1-9-0-1[4]-1-1-1\"]/div/h2[text()='iPhone Versions']");
    private By UnlockedCheckBox = By.xpath("//*[@id=\"s0-28_1-9-0-1[0]-0-0-6-8-4[4]-flyout\"]/div/ul/li[1]/a/span[2][text()='Unlocked']/..");
    private By UnlockedIphoneTitle = By.xpath("/html/body/div[3]/div[2]/h1/span[text()='Apple Unlocked Smartphones']");
    private By IphoneResults = By.xpath("/html/body/div[3]/div[3]/div[3]/section[1]/div[1]/div[2]/div/div/h2[text()='3,463 Results']");

    WebDriver driver;
    Select select;

    @BeforeMethod
    public void openBrowser() {
        //Select Chrome
        WebDriverManager.chromedriver().setup();
        //Double-click on Chrome
        driver = new ChromeDriver();
        //Go to ebay homepage
        ActOn.browser(driver).openBrowser("https://www.ebay.com/");

    }

    public void navigateToRefurbishedPage(){
        //Mouse hover to Electronics link
//        Actions actions = new Actions(driver);
//        actions.moveToElement(driver.findElement(ElectronicsTab)).perform();
        ActOn.element(driver, ElectronicsTab).mouseHover();

        //Setting a 3-second time so Refurbished Link can be present
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        ActOn.wait(driver, RefurbishedLink).waitForElementToBeVisible();

        //Click on Ebay Refurbished Link
//        driver.findElement(RefurbishedLink).click();
        ActOn.element(driver, RefurbishedLink).click();

        //Wait for page to load
        //Also acts as a verification because 'eBay Refurbished' needs to be displayed on webpage
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//        wait.until(ExpectedConditions.visibilityOfElementLocated(RefurbishedIcon));
        ActOn.wait(driver, RefurbishedIcon).waitForElementToBeVisible();
    }

    public void navigateToIphonePage(){
        //mouse over didn't work so more direct approach click on Iphone link
//        driver.findElement(IphoneLink).click();
        ActOn.element(driver, IphoneLink).click();

        //Use JavascriptExecutor in order to access some manual tasks like scrolling
//        JavascriptExecutor js=(JavascriptExecutor)driver;
        //Use this to manually scroll down the page to location of 16200 aka 'iPhone Version'
  //      js.executeScript("window.scrollTo(0, 16200)");

        //The below finds the web element which I saved via xpath & scrolls until that element is found
        //This execution allows me to scroll until I find the specific element 'iPhone Versions'
//        WebElement iPhoneVersions = driver.findElement(IphoneVersion);
//        js.executeScript("arguments[0].scrollIntoView(true)", iPhoneVersions);
        ActOn.element(driver, IphoneVersion).scrollIntoView();

        //Wait for page to load & verify
        //'iPhone Versions' should be displayed near the bottom of the page
//              WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
//              wait.until(ExpectedConditions.visibilityOfElementLocated(IphoneVersion));
        ActOn.wait(driver, IphoneVersion).waitForElementToBeVisible();


    }

    public void selectNetwork(){
        //Use this to scroll back up the page to find 'Network' dropdown
//        JavascriptExecutor js=(JavascriptExecutor)driver;
//        js.executeScript("window.scrollTo(0,100)");
        ActOn.element(driver, NetworkDropDown).scrollIntoView().click();

        //Setting a 3-second time after scrolling to the top of the page
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        //Wait for page to load & verify
        //'Network' dropdown tab should be displayed near the top of the page
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
//        wait.until(ExpectedConditions.visibilityOfElementLocated(NetworkDropDown));

        //Mouse hover to Network dropdown & select Unlocked
//        Actions actions = new Actions(driver);
//        actions.moveToElement(driver.findElement(NetworkDropDown)).click();
        //Select Unlocked
//        driver.findElement(UnlockedCheckBox).click();
        //Click on Network dropdown & Select Unlocked checkbox
//        driver.findElement(NetworkDropDown).click();
        //Click on Unlocked check box
//        driver.findElement(UnlockedCheckBox).click();
        ActOn.element(driver, UnlockedCheckBox).click();

    //    select = new Select(driver.findElement(NetworkDropDown));
    //    select.selectByVisibleText("Unlocked");
    }

    @Test
    public void verifyRefurbishedIphone(){
        //Complete all the other functions from the Before method by calling them before test
        navigateToRefurbishedPage();
        navigateToIphonePage();
        selectNetwork();

        //Setting a 3-second time after above navigation to ensure actual UAT experience
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        //Pull the title of the page, which should have 'Apple' or 'Iphone' with 'Unlocked'
        String expectedTitle = "Apple Unlocked Smartphones for Sale | Shop New & Used Cell Phones | eBay";
        String actualPageTitle = ActOn.browser(driver).captureTitle();

        //Verify user is on the unlocked iphone page
        Assert.assertEquals(expectedTitle,actualPageTitle);


        //Put the expected value '3,216 Results' in string format & use boolean to verify the formattedxpath string is present
        //I manually perform actions to find the results value then update the test before running
        String expectedResult = "3,216 Results";
        String formattedXpath = String.format("/html/body/div[3]/div[3]/div[3]/section[1]/div[1]/div[2]/div/div/h2[text()='3,216 Results']", expectedResult);

        By results = By.xpath(formattedXpath);

        AssertThat.elementAssertions(driver, results).elementIsDisplayed();

//        boolean present = driver.findElement(By.xpath(formattedXpath)).isDisplayed();
    }

    @AfterMethod
    public void closeBrowser(){
        //close chrome
//        driver.quit();
//        ActOn.browser(driver).closeBrowser();
    }
}
