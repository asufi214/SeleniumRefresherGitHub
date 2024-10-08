package automation_test.Calculator;

import command_providers.ActOn;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page_objects.MortgageCalculator;

public class MortgageCalculatorPgObj {

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
    private By ActualMonthlyMortgage = By.xpath("//tr[2]/td[2]/b[text()='$2,252.94']");


    //Declaring Webdriver & Select so we can use chrome driver & Select dropdowns globally (outside the methods)
    WebDriver driver;

    @BeforeMethod
    public void openBrowser() {

        //Open Chrome
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        //Go to Calculator homepage
        ActOn.browser(driver).openBrowser("https://www.calculator.net/");

    }

    @Test
    public void verifyMortgagePage() throws InterruptedException {
        new MortgageCalculator(driver)
                .clickMortgageLink()
                .waitForPage()
                .verifyMortgagePage()
                .clearMortgagePage()
                .enterHomeValue("350000")
                .selectDollarDrop("$")
                .enterDownPayment("70000")
                .enterLoanTerm("30")
                .enterInterestRate("9")
                .selectMonth("Jun")
                .enterYear("2024")
                .selectPercentPropertyTax("%")
                .enterPropertyTax("7")
                .enterHomeInsurance("15000")
                .selectPmiPercent("%")
                .enterPmi("3")
                .clickCalculate()
                .verifyRate("$2,252.94")
                .verifyRate2("$2,252.94");

    }

    @AfterMethod

    public void closeBrowser(){
        //closes the browser
        ActOn.browser(driver).closeBrowser();
    }
}
