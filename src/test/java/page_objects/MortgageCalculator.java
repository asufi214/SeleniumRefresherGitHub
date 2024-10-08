package page_objects;

import command_providers.ActOn;
import command_providers.AssertThat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import utlities.SqlConnector;

import java.sql.ResultSet;
import java.time.Duration;

public class MortgageCalculator {
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
    private By ActualMonthlyMortgage = By.xpath("//tr[2]/td[2]/b[text()='$2,252.94']");

    private static final Logger LOGGER = LogManager.getLogger(MortgageCalculator.class);

    public WebDriver driver;

    public MortgageCalculator(WebDriver driver) {
        this.driver = driver;
    }

    public MortgageCalculator clickMortgageLink() {
        LOGGER.debug("Clicking on Mortgage Link");
        ActOn.element(driver, MortgageLink).click();
        return this;
    }

    public MortgageCalculator waitForPage() {
            LOGGER.debug("Setting a 5sec wait for pg to load");
            //Setting a 3-second time after above navigation to ensure actual UAT experience
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            return this;
        }

    public MortgageCalculator verifyMortgagePage() {
        LOGGER.debug("Verifying Mortgage page title");
        String expectedPage = "Mortgage Calculator";
        String actualPage = driver.getTitle();

        //Validate the Mortgage Calculator Page
        Assert.assertEquals(actualPage, expectedPage);
        return this;
    }

    public MortgageCalculator clearMortgagePage() {
        LOGGER.debug("Clearing the Mortgage Calculator home page");
        ActOn.element(driver, MortgageCalLink).click();
        return this;
    }

    public MortgageCalculator enterHomeValue(String value) {
        LOGGER.debug("Entering Home Value (350000): " + value);
        ActOn.element(driver, HomeValueInput).setValue(value);
        return this;
    }

    public MortgageCalculator selectDollarDrop(String value) {
        LOGGER.debug("Selecting $ for down payment amount: " + value);
        ActOn.element(driver, DownPay$Drop).selectValue(value);
        return this;
    }

    public MortgageCalculator enterDownPayment(String value) {
        LOGGER.debug("Entering down payment of (70000): " + value);
        ActOn.element(driver, DownPayment).setValue(value);
        return this;
    }

    public MortgageCalculator enterLoanTerm(String value) {
        LOGGER.debug("Entering the Loan term of 30: " + value);
        ActOn.element(driver, LoanTerm).setValue(value);
        return this;
    }

    public MortgageCalculator enterInterestRate(String value) {
        LOGGER.debug("Entering an interest rate of 9%: " + value);
        ActOn.element(driver, InterestRate).setValue(value);
        return this;
    }

    public MortgageCalculator selectMonth(String value) {
        LOGGER.debug("Select the start date month (June): " + value);
        ActOn.element(driver, StartMonth).selectValue(value);
        return this;
    }

    public MortgageCalculator enterYear(String value) {
        LOGGER.debug("Entering 2024 for year: " + value);
        ActOn.element(driver, StartYear).setValue(value);
        return this;
    }

    public MortgageCalculator selectPercentPropertyTax(String value) {
        LOGGER.debug("Selecting % for the property tax: " + value);
        ActOn.element(driver, PropertyTaxDrop).selectValue(value);
        return this;
    }

    public MortgageCalculator enterPropertyTax(String value) {
        LOGGER.debug("Entering 7% for Property Tax: " + value);
        ActOn.element(driver, PropertyTax).setValue(value);
        return this;
    }

    public MortgageCalculator enterHomeInsurance(String value) {
        LOGGER.debug("Enter $15000 for home insurance: " + value);
        ActOn.element(driver, HomeInsurance).setValue(value);
        return this;
    }

    public MortgageCalculator selectPmiPercent(String value) {
        LOGGER.debug("Selecting % for PMI Insurance: " + value);
        ActOn.element(driver, InsuranceUnit).selectValue(value);
        return this;
    }

    public MortgageCalculator enterPmi(String value) throws InterruptedException {
        LOGGER.debug("Enter 3% for PMI Insurance: " + value);
        ActOn.element(driver, PmiField).setValue(value);
        Thread.sleep(100);
        return this;
    }

    public MortgageCalculator clickCalculate() {
        LOGGER.debug("Clicking on Calculate button");
        ActOn.element(driver, CalculateButton).click();
        return this;
    }

    public MortgageCalculator verifyRate(String expectedMonthlyRate) {
        LOGGER.debug("Verifying the actual monthly rate of $2,252.94");
        String monthlyMortgage = ActOn.element(driver, ActualMonthlyMortgage).getTextValue();
        Assert.assertEquals(monthlyMortgage, expectedMonthlyRate);
        return this;
    }

    public MortgageCalculator verifyRate2(String expectedTotalMonthlyPayment) {
      //  String expectedTotalMonthlyPayment = "2,252.94";
        String formattedXpath = String.format("//tr[2]/td[2]/b[text()='%s']", expectedTotalMonthlyPayment);

        By monthlyPayment = By.xpath(formattedXpath);
        LOGGER.debug("Validating that the total monthly payment is: " + expectedTotalMonthlyPayment);
        AssertThat.elementAssertions(driver, monthlyPayment).elementIsDisplayed();
        return this;
    }

}
