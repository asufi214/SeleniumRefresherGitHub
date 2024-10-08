package automation_test.Calculator;

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

public class InvestCalculator {
    WebDriver driver;
    Select select;

    private By InvestCalLink = By.linkText("Investment Calculator");
    private By StartAmtField = By.id("cstartingprinciplev");
    private By AfterYrField = By.id("cyearsv");
    private By ReturnRate = By.name("cinterestratev");
    private By CompoundDrop = By.id("ccompound");
    private By AddContribute = By.name("ccontributeamountv");
    private By BeginRadioB = By.xpath("//*[@id=\"cperiod\"]/tbody/tr[1]/td/label[1]/span");
    private By MonthRadioB = By.xpath("//*[@id=\"cperiod\"]/tbody/tr[1]/td/label[3]/span");
    private By CalculateB = By.name("x");
    private By ActualEndBalance = By.xpath("//tbody/tr[1]/td[2]/b[text()='$48,294.26']");

    @BeforeMethod
    public void openBrowser(){
        //Select Chrome
        WebDriverManager.chromedriver().setup();
        //Double-click on chrome
        driver = new ChromeDriver();
        ActOn.browser(driver).openBrowser("https://www.calculator.net/");

        //Double-click on Investment Calculator Link
        ActOn.element(driver, InvestCalLink).click();
    }

    public void enterEndAmountData(){
        //Enter 10k stating amount
        ActOn.element(driver, StartAmtField).setValue("10000");

        //Enter 5 years
        ActOn.element(driver, AfterYrField).setValue("5");

        //Enter 6% Return Rate
        ActOn.element(driver, ReturnRate).setValue("6");

        //Select Annually from compound dropdown
        ActOn.element(driver, CompoundDrop).selectValue("annually");

        //Enter $500 for additional contributions
        ActOn.element(driver, AddContribute).setValue("500");

        //Select the beginning contribution radio button
        ActOn.element(driver, BeginRadioB).click();

        //Select monthly from the contribution radio button
        ActOn.element(driver, MonthRadioB).click();

        //Click on Calculate button
        ActOn.element(driver, CalculateB).click();

    }

    @Test
    public void VerifyInvestCalculator(){
        //Enter all the data on the investment page
        enterEndAmountData();

        //Pull the title of the page
        String expectedTitle = "Investment Calculator";
        String actualPageTitle = ActOn.browser(driver).captureTitle();

        //Verify user is on the Investment Calculator page
        Assert.assertEquals(expectedTitle,actualPageTitle);

        //Put the expected value $48,294.26 in string format & use elementAssertions to verify the formattedxpath string is present
        String expectedEndBalance = "$48,294.26";
        String formattedXpath = String.format("//tbody/tr[1]/td[2]/b[text()='$48,294.26']", expectedEndBalance);

        By endBalance = By.xpath(formattedXpath);

        //Validate that the total monthly payment is "Monthly Pay:   $2,152.96"
        AssertThat.elementAssertions(driver, endBalance).elementIsDisplayed();

        //I can do the same assertions from above(82-88) using fewer lines of code
        String expectedBalance = ActOn.element(driver, ActualEndBalance).getTextValue();
        Assert.assertEquals(expectedBalance, "$48,294.26");
    }

    @AfterMethod
    public void closeBrowser(){
        //close Chrome
        ActOn.browser(driver).closeBrowser();
    }
}

