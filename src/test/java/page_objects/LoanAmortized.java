package page_objects;

import command_providers.ActOn;
import command_providers.AssertThat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.time.Duration;

public class LoanAmortized {
    private By LoanCalLink = By.xpath("/html/body/div[4]/div/div[1]/div[1]/ul/li[2]/a");
    private By LoanAmount = By.name("cloanamount");
    private By LoanTerm = By.name("cloanterm");
    private By LoanMth = By.name("cloantermmonth");
    private By InterestRate = By.name("cinterestrate");
    private By CompoundDrop = By.name("ccompound");
    private By PayBackTerm = By.name("cpayback");
    private By CalculateLoan = By.name("x");

    private static final Logger LOGGER = LogManager.getLogger(LoanAmortized.class);

    public WebDriver driver;

    public LoanAmortized(WebDriver driver) {
        this.driver = driver;
    }

    public LoanAmortized clickOnLoanCalLink() {
        LOGGER.debug("Clicking on the Loan Calculator link");
        ActOn.element(driver, LoanCalLink).click();
        return this;
    }

    public LoanAmortized typeLoanAmount(String value) {
        LOGGER.debug("Typing the search: " + value);
        ActOn.element(driver, LoanAmount).setValue(value);
        return this;
    }

    public LoanAmortized typeLoanTermYear(String value) {
        LOGGER.debug("Entering 30 Year for loan amount year");
        ActOn.element(driver, LoanTerm).setValue(value);
        return this;
    }

    public LoanAmortized typeLoanTermMonth(String value) {
        LOGGER.debug("Entering 1 month for loan amount month");
        ActOn.element(driver, LoanMth).setValue(value);
        return this;
    }

    public LoanAmortized typeInterestRate(String value) {
        LOGGER.debug("Entering interest rate of 7.5");
        ActOn.element(driver, InterestRate).setValue(value);
        return this;
    }

    public LoanAmortized selectQuarterlyComDropD(String value) {
        LOGGER.debug("Selecting Quarterly from compound dropdown");
        ActOn.element(driver, CompoundDrop).selectValue(value);
        return this;
    }

    public LoanAmortized selectQuarterlyPayDropD(String value) {
        LOGGER.debug("Selecting Every Quarter from Payback dropdown");
        ActOn.element(driver, PayBackTerm).selectValue(value);
        return this;
    }

    public LoanAmortized clickCalculate() {
        LOGGER.debug("Clicking calculate button");
        ActOn.element(driver, CalculateLoan).click();
        return this;
    }

    public LoanAmortized waitForPg() {
        LOGGER.debug("Setting a 5sec wait for pg to load");
        //Setting a 3-second time after above navigation to ensure actual UAT experience
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        return this;
    }

    public LoanAmortized verifyLoanPage() {
        LOGGER.debug("Validate user landed on Loan Calculator page");
        //Get page title
        String expectedPage = "Loan Calculator";
        String actualPage = ActOn.browser(driver).captureTitle();
        Assert.assertEquals(actualPage, expectedPage);
        return this;
    }

    public LoanAmortized verifyQuarterAmount(String pullQrtPaymentValue) {
        LOGGER.debug("Validate Quarterly repayment amount of $7348.44");
        String expectedQrtPayment = "$7,348.44";
        String formattedXpath = String.format("/html/body/div[3]/div[1]/div[3]/table/tbody/tr[1]/td[2]/b[text()='$7,348.44']", pullQrtPaymentValue);

        By pullQrtPayment = By.xpath(formattedXpath);
        LOGGER.debug("Validating that Quarterly repayment amount is: " + pullQrtPaymentValue);
        AssertThat.elementAssertions(driver, pullQrtPayment).elementIsDisplayed();
        return this;
    }

}
