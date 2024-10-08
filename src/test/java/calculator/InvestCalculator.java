package calculator;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class InvestCalculator {
    WebDriver driver;
    Select select;

    private By InvestCalLink = By.xpath("/html/body/div[4]/div/div[1]/div[1]/ul/li[8]/a");
    private By StartAmtField = By.id("cstartingprinciplev");
    private By AfterYrField = By.id("cyearsv");
    private By ReturnRate = By.name("cinterestratev");
    private By CompoundDrop = By.id("ccompound");
    private By AddContribute = By.name("ccontributeamountv");
    private By BeginRadioB = By.xpath("//div[1]/table/tbody/tr/td[1]/form/table[6]/tbody/tr[1]/td/label[1]/span");
    private By MonthRadioB = By.xpath("//div[1]/table/tbody/tr/td[1]/form/table[6]/tbody/tr[1]/td/label[3]/span");
    private By CalculateB = By.name("x");

    @BeforeMethod
    public void openBrowser(){
        //Select Chrome
        WebDriverManager.chromedriver().setup();
        //Double-click on chrome
        driver = new ChromeDriver();
        //Go to Calculate page
        driver.get("https://www.calculator.net/");
        //Maximize the page
        driver.manage().window().maximize();
        //Double-click on Investment Calculator Link
        driver.findElement(InvestCalLink).click();
    }

    public void enterEndAmountData(){
        //Enter 10k stating amount
        driver.findElement(StartAmtField).clear();
        driver.findElement(StartAmtField).sendKeys("10000");

        //Enter 5 years
        driver.findElement(AfterYrField).clear();
        driver.findElement(AfterYrField).sendKeys("5");

        //Enter 6% Return Rate
        driver.findElement(ReturnRate).clear();
        driver.findElement(ReturnRate).sendKeys("6");

        //Select Annually from compound dropdown
        select = new Select(driver.findElement(CompoundDrop));
        select.selectByVisibleText("annually");

        //Enter $500 for additional contributions
        driver.findElement(AddContribute).clear();
        driver.findElement(AddContribute).sendKeys("500");

        //Select the beginning contribution radio button
        driver.findElement(BeginRadioB).click();

        //Select monthly from the contribution radio button
        driver.findElement(MonthRadioB).click();

        //Click on Calculate button
        driver.findElement(CalculateB).click();

    }

    @Test
    public void VerifyInvestCalculator(){
        //Enter all the data on the investment page
        enterEndAmountData();

        //Pull the title of the page
        String expectedTitle = "Investment Calculator";
        String actualPageTitle = driver.getTitle();

        //Verify user is on the Investment Calculator page
        Assert.assertEquals(expectedTitle,actualPageTitle);

        //Put the expected value $48,294.26 in string format & use boolean to verify the formattedxpath string is present
        String expectedEndBalance = "$48,294.26";
        String formattedXpath = String.format("//tbody/tr[1]/td[2]/b[text()='$48,294.26']");
        boolean present = driver.findElement(By.xpath(formattedXpath)).isDisplayed();
    }

    @AfterMethod
    public void closeBrowser(){
        //close Chrome
        driver.quit();
    }
}

