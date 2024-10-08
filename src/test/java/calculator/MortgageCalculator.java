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

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class MortgageCalculator {

    //By declaring out side of the openBrowser, verifyMortgagePage methods & before annotations like @Test we are making them global
    //This will make it easier to access & update should we need to later on.
    private By MortgageCalLink = By.xpath("/html/body/div[3]/div[1]/div[4]/div/form/table/tbody/tr[8]/td/input[3]");
    private By HomeValueInput = By.name("chouseprice");
    private By DownPayment = By.name("cdownpayment");
    private By DownPay$Drop = By.name("cdownpaymentunit");
    private By LoanTerm = By.name("cloanterm");
    private By InterestRate = By.name("cinterestrate");
    private By StartMonth = By.name("cstartmonth");
    private By StartYear = By.name("cstartyear");
    private By PropertyTaxDrop = By.name("cpropertytaxesunit");
    private By PropertyTax = By.name("cpropertytaxes");
    private By HomeInsurance = By.name("chomeins");
    private By InsuranceUnit = By.name("cpmiunit");
    private By PmiField = By.name("cpmi");
    private By CalculateButton = By.name("x");


    //Declaring Webdriver & Select so we can use chrome driver & Select dropdowns globally (outside the methods)
    WebDriver driver;
    Select select;

    @BeforeMethod
    public void openBrowser() {

        //Open Chrome
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        //Go to Calculator homepage
        driver.get("https://www.calculator.net/");
        //Maximize the webpage
        driver.manage().window().maximize();
        //Click on Mortgage Calculator link
        driver.findElement(By.linkText("Mortgage Calculator")).click();
    }

    @Test
    public void verifyMortgagePage() throws InterruptedException {
        String expectedPage = "Mortgage Calculator";
        String actualPage = driver.getTitle();

        //Validate the Mortgage Calculator Page
        Assert.assertEquals(actualPage, expectedPage);

        //Clear the Mortgage Calculator page
        driver.findElement(MortgageCalLink).click();

        //Enter Home Value "350000"
     //   driver.findElement(HomeValueInput).clear();
        driver.findElement(HomeValueInput).sendKeys("350000");

        //Select $ for down payment amount
        select = new Select(driver.findElement(DownPay$Drop));
        select.selectByVisibleText("$");

        //Enter down payment "70000"
    //    driver.findElement(DownPayment).clear();
        driver.findElement(DownPayment).sendKeys("70000");

        //Enter loan term
    //    driver.findElement(LoanTerm).clear();
        driver.findElement(LoanTerm).sendKeys("30");

        //Enter an interest rate of 8.5%
    //    driver.findElement(InterestRate).clear();
        driver.findElement(InterestRate).sendKeys("8.5");

        //Select the start date month "June"
        select = new Select(driver.findElement(StartMonth));
        select.selectByVisibleText("Jun");
        //Enter 2024 for year
    //    driver.findElement(StartYear).clear();
        driver.findElement(StartYear).sendKeys("2024");

        //Select % for the property tax
        select = new Select(driver.findElement(PropertyTaxDrop));
        select.selectByVisibleText("%");

        //Enter 7% for Property Tax
    //    driver.findElement(PropertyTax).clear();
        driver.findElement(PropertyTax).sendKeys("7");

        //Enter $15000 for home insurance
        driver.findElement(HomeInsurance).sendKeys("15000");

        //Select % for PMI Insurance
        select = new Select(driver.findElement(InsuranceUnit));
        select.selectByVisibleText("%");

        //Enter 3% for PMI Insurance
        driver.findElement(PmiField).sendKeys("3");
        Thread.sleep(100);

        //Click on Calculate button
        driver.findElement(CalculateButton).click();

        //Inserting an implicitewait of 15seconds so everything is displayed before assertions
     //   driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
     //   verifyMortgagePage();

        //Validate that the total monthly payment is "Monthly Pay:   $2,152.96"
        String expectedTotalMonthlyPayment = "2,152.96";
        String formattedXpath = String.format("//tr[2]/td[2]/b[text()='$%s']", expectedTotalMonthlyPayment);
        boolean present = driver.findElement(By.xpath(formattedXpath)).isDisplayed();

        //If the monthly payment is different from the expected then the below message will be displayed
        Assert.assertTrue(present, "Total Monthly Payment is not presented");

    }

    @AfterMethod

    public void closeBrowser(){
        //closes the browser
        driver.quit();
    }
}
