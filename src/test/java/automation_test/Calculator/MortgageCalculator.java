package automation_test.Calculator;

import command_providers.ActOn;
import command_providers.AssertThat;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class MortgageCalculator {

    //By declaring out side of the openBrowser, verifyMortgagePage methods & before annotations like @Test we are making them global
    //This will make it easier to access & update should we need to later on.
    private By MortgageCalLink = By.xpath("/html/body/div[3]/div[1]/div[4]/div/form/table/tbody/tr[8]/td/input[3]");
    private By MortgageLink = By.linkText("Mortgage Calculator");
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
    private By ActualMonthlyMortgage = By.xpath("//tr[2]/td[2]/b[text()='$2,152.96']");


    //Declaring Webdriver & Select so we can use chrome driver & Select dropdowns globally (outside the methods)
    WebDriver driver;


    @BeforeMethod
    public void openBrowser() {

        //Open Chrome
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        //Go to Calculator homepage
        ActOn.browser(driver).openBrowser("https://www.calculator.net/");

        //Click on Mortgage Calculator link
        ActOn.element(driver, MortgageLink).click();

    }

    @Test
    public void verifyMortgagePage() throws InterruptedException {
        String expectedPage = "Mortgage Calculator";
        String actualPage = driver.getTitle();

        //Validate the Mortgage Calculator Page
        Assert.assertEquals(actualPage, expectedPage);

        //Clear the Mortgage Calculator page
        ActOn.element(driver, MortgageCalLink).click();

        //Enter Home Value "350000"
     //   driver.findElement(HomeValueInput).clear();
        ActOn.element(driver, HomeValueInput).setValue("350000");

        //Select $ for down payment amount
  //      select = new Select(driver.findElement(DownPay$Drop));
  //      select.selectByVisibleText("$");
        ActOn.element(driver, DownPay$Drop).selectValue("$");

        //Enter down payment "70000"
    //    driver.findElement(DownPayment).clear();
        ActOn.element(driver, DownPayment).setValue("70000");

        //Enter loan term
    //    driver.findElement(LoanTerm).clear();
        ActOn.element(driver, LoanTerm).setValue("30");

        //Enter an interest rate of 8.5%
    //    driver.findElement(InterestRate).clear();
        ActOn.element(driver, InterestRate).setValue("8.5");

        //Select the start date month "June"
//        select = new Select(driver.findElement(StartMonth));
//        select.selectByVisibleText("Jun");
        ActOn.element(driver, StartMonth).selectValue("Jun");

        //Enter 2024 for year
    //    driver.findElement(StartYear).clear();
        ActOn.element(driver, StartYear).setValue("2024");

        //Select % for the property tax
//        select = new Select(driver.findElement(PropertyTaxDrop));
//        select.selectByVisibleText("%");
        ActOn.element(driver, PropertyTaxDrop).selectValue("%");

        //Enter 7% for Property Tax
    //    driver.findElement(PropertyTax).clear();
        ActOn.element(driver, PropertyTax).setValue("7");

        //Enter $15000 for home insurance
        ActOn.element(driver, HomeInsurance).setValue("15000");

        //Select % for PMI Insurance
//        select = new Select(driver.findElement(InsuranceUnit));
//        select.selectByVisibleText("%");
        ActOn.element(driver, InsuranceUnit).selectValue("%");

        //Enter 3% for PMI Insurance
        ActOn.element(driver, PmiField).setValue("3");
        Thread.sleep(100);

        //Click on Calculate button
        ActOn.element(driver, CalculateButton).click();

        //Inserting an implicitewait of 15seconds so everything is displayed before assertions
     //   driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
     //   verifyMortgagePage();

        //Using the below 124-125lines we can do the same as 127-133, expected rate is $2,152.96
        String monthlyMortgage = ActOn.element(driver, ActualMonthlyMortgage).getTextValue();
        Assert.assertEquals(monthlyMortgage, "$2,152.96");

        String expectedTotalMonthlyPayment = "2,152.96";
        String formattedXpath = String.format("//tr[2]/td[2]/b[text()='$%s']", expectedTotalMonthlyPayment);

        By monthlyPayment = By.xpath(formattedXpath);

        //Validate that the total monthly payment is "Monthly Pay:   $2,152.96"
        AssertThat.elementAssertions(driver, monthlyPayment).elementIsDisplayed();

    }

    @AfterMethod

    public void closeBrowser(){
        //closes the browser
        ActOn.browser(driver).closeBrowser();
    }
}
