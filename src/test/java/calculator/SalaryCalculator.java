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

public class SalaryCalculator {

    private By SalaryCalLink = By.xpath("//div[1]/ul/li[13]/a[text()='Salary Calculator']");
    private By SalaryAmount = By.name("camount");
    private By PerDropDown = By.name("cunit");
    private By HrsField = By.name("chours");
    private By DayField = By.name("cdays");
    private By HolidayField = By.name("cholidays");
    private By VacaDayField = By.name("cvacation");
    private By CalculateButton = By.name("x");

    WebDriver driver;
    Select select;

    @BeforeMethod
    public void openBrowser() {
        //Select Chrome
        WebDriverManager.chromedriver().setup();
        //Open up a Chrome window
        driver = new ChromeDriver();
        //Go to Calculator.net
        driver.get("https://www.calculator.net/");
        //Maximize the window
        driver.manage().window().maximize();
        //Navigate to Salary Calculator page
        driver.findElement(SalaryCalLink).click();
    }

    public void enterData() {
        //Enter $58 in Salary Amount field
        driver.findElement(SalaryAmount).clear();
        driver.findElement(SalaryAmount).sendKeys("58");

        //Select Hourly from Drowdown
        select = new Select(driver.findElement(PerDropDown));
        select.selectByVisibleText("Hour");

        //Enter 40 for hours per week
        driver.findElement(HrsField).clear();
        driver.findElement(HrsField).sendKeys("40");

        //Enter 5 for days per week
        driver.findElement(DayField).clear();
        driver.findElement(DayField).sendKeys("5");

        //Enter 14 Holiday per week
        driver.findElement(HolidayField).clear();
        driver.findElement(HolidayField).sendKeys("14");

        //Enter 18 Vacation days per year
        driver.findElement(VacaDayField).clear();
        driver.findElement(VacaDayField).sendKeys("18");

        //Click on Calculate Button
        driver.findElement(CalculateButton).click();
    }

    @Test
    public void verifySalaryCalculator() {
        //Enter the data on Salary page
        enterData();

        //Pull the page title
        String expectedTitle = "Salary Calculator";
        String actualTitle = driver.getTitle();
        //Verify user lands on Salary Calculator page
        Assert.assertEquals(expectedTitle, actualTitle);

        //Expected yearly salary is 120,640
        String expectedAnnualSalary = "$120,640";
        //pulling the string value from the formatted xpath.
        String formattedXpath = String.format("//div[3]/table/tbody/tr[9]/td[2][text()='%s']",expectedAnnualSalary);
        //Using boolean to verify formatted xpath is displayed
        boolean present = driver.findElement(By.xpath(formattedXpath)).isDisplayed();

    }

    @AfterMethod
    public void closeBrowser(){
        //close chrome
        driver.quit();
    }

}
