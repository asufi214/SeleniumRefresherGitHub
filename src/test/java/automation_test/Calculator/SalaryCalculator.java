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

public class SalaryCalculator {

    private By SalaryCalLink = By.linkText("Salary Calculator");
    private By SalaryAmount = By.name("camount");
    private By PerDropDown = By.name("cunit");
    private By HrsField = By.name("chours");
    private By DayField = By.name("cdays");
    private By HolidayField = By.name("cholidays");
    private By VacaDayField = By.name("cvacation");
    private By CalculateButton = By.name("x");

    WebDriver driver;

    @BeforeMethod
    public void openBrowser() {
        //Select Chrome
        WebDriverManager.chromedriver().setup();
        //Open up a Chrome window
        driver = new ChromeDriver();
        //Go to Calculator.net
        ActOn.browser(driver).openBrowser("https://www.calculator.net/");

        //Navigate to Salary Calculator page
        ActOn.element(driver, SalaryCalLink).click();

    }

    public void enterData() {
        //Enter $58 in Salary Amount field
        ActOn.element(driver, SalaryAmount).setValue("58");

        //Select Hourly from Drowdown
        ActOn.element(driver, PerDropDown).selectValue("Hour");

        //Enter 40 for hours per week
        ActOn.element(driver, HrsField).setValue("40");

        //Enter 5 for days per week
        ActOn.element(driver, DayField).setValue("5");

        //Enter 14 Holiday per week
        ActOn.element(driver, HolidayField).setValue("14");

        //Enter 18 Vacation days per year
        ActOn.element(driver, VacaDayField).setValue("18");

        //Click on Calculate Button
        ActOn.element(driver, CalculateButton).click();
    }

    @Test
    public void verifySalaryCalculator() {
        //Enter the data on Salary page
        enterData();

        //Pull the page title
        String expectedTitle = "Salary Calculator";
        String actualTitle = ActOn.browser(driver).captureTitle();

        //Verify user lands on Salary Calculator page
        Assert.assertEquals(expectedTitle, actualTitle);

        //Expected yearly salary is 120,640
        String expectedAnnualSalary = "$120,640";
        //pulling the string value from the formatted xpath.
        String formattedXpath = String.format("//div[3]/table/tbody/tr[9]/td[2][text()='%s']",expectedAnnualSalary);
        //Using boolean to verify formatted xpath is displayed
        By actualAnnualSalary = By.xpath(formattedXpath);

        AssertThat.elementAssertions(driver, actualAnnualSalary).elementIsDisplayed();
//        boolean present = driver.findElement(By.xpath(formattedXpath)).isDisplayed();

    }

    @AfterMethod
    public void closeBrowser(){
        //close chrome
        ActOn.browser(driver).closeBrowser();
    }

}
