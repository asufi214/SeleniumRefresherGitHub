package automation_test.Calculator;

import com.beust.ah.A;
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

public class AmortizedLoan {

    private By LoanCalLink = By.xpath("/html/body/div[4]/div/div[1]/div[1]/ul/li[2]/a");
    private By LoanAmount = By.name("cloanamount");
    private By LoanTerm = By.name("cloanterm");
    private By LoanMth = By.name("cloantermmonth");
    private By InterestRate = By.name("cinterestrate");
    private By CompoundDrop = By.name("ccompound");
    private By PayBackTerm = By.name("cpayback");
    private By CalculateLoan = By.name("x");

// 350000, 30, 1, 7, Quarterly, Every Quarter
    ///html/body/div[3]/div[1]/div[3]/table/tbody/tr[1]/td[2]/b[contains(text(),'$6,991.86')]

    //Declaring Webdriver & Select so we can use chrome driver & Select dropdowns globally (outside the methods)
    WebDriver driver;
    Select select;

    @BeforeMethod
    public void openBrowser(){
        //Open Chrome
        WebDriverManager.chromedriver().setup();
    //    WebDriverManager.chromedriver().clearDriverCache();
        driver = new ChromeDriver();
        //Go to Calculate.net HP
        ActOn.browser(driver).openBrowser("https://www.calculator.net/");

        //Click on Loan Calculator
        ActOn.element(driver, LoanCalLink).click();
    }

    public void enterData(){
        //Enter 350k loan amount
        ActOn.element(driver, LoanAmount).setValue("350000");

        //Enter 30 Year & 1 month for loan amount
        ActOn.element(driver, LoanTerm).setValue("30");
        ActOn.element(driver, LoanMth).setValue("1");

        //Enter Interest rate of 7.5
        ActOn.element(driver, InterestRate).setValue("7.5");

        //Select Quarterly from compound dropdown
        ActOn.element(driver, CompoundDrop).selectValue("Quarterly");
//        Select select = new Select(driver.findElement(CompoundDrop));
//        select.selectByVisibleText("Quarterly");
        //Select Every Quarter from Payback dropdown
        ActOn.element(driver, PayBackTerm).selectValue("Every Quarter");
//        Select select1 = new Select(driver.findElement(PayBackTerm));
//        select1.selectByVisibleText("Every Quarter");
        //Click on calculate button
        ActOn.element(driver, CalculateLoan).click();

    }

    @Test
    public void VerifyAmortizedLoanFunction(){
        //Call enterData actions before the test functions
        enterData();

        //Get page title
        String expectedPage = "Loan Calculator";
        String actualPage = ActOn.browser(driver).captureTitle();

        //Validate user landed on Loan Calculator page
        Assert.assertEquals(actualPage,expectedPage);

        //Validate Quarterly repayment amount of $7348.44
        String expectedQrtPayment = "$7,348.44";
        String formattedXpath = String.format("/html/body/div[3]/div[1]/div[3]/table/tbody/tr[1]/td[2]/b[text()='$7,348.44']");

        By pullQrtPayment = By.xpath(formattedXpath);

        //Validate that the total monthly repayment amount of $7348.44"
        AssertThat.elementAssertions(driver, pullQrtPayment).elementIsDisplayed();

    }

    @AfterMethod
    public void closeBrowser(){
        ActOn.browser(driver).closeBrowser();
    }

}
