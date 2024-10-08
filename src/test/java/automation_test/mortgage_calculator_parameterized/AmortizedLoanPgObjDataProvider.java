package automation_test.mortgage_calculator_parameterized;

import command_providers.ActOn;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page_objects.LoanAmortized;
import parameters.DataProviderClass;
import utlities.ReadConfigFiles;

public class AmortizedLoanPgObjDataProvider {

    private By LoanCalLink = By.xpath("/html/body/div[4]/div/div[1]/div[1]/ul/li[2]/a");
    private By LoanAmount = By.name("cloanamount");
    private By LoanTerm = By.name("cloanterm");
    private By LoanMth = By.name("cloantermmonth");
    private By InterestRate = By.name("cinterestrate");
    private By CompoundDrop = By.name("ccompound");
    private By PayBackTerm = By.name("cpayback");
    private By CalculateLoan = By.name("x");

// 350000, 30, 1, 7, Quarterly, Every Quarter, $7348.44
    private static final Logger LOGGER = LogManager.getLogger(AmortizedLoanPgObjDataProvider.class);
    WebDriver driver;

    @BeforeMethod
    public void openBrowser() {
        WebDriverManager.chromedriver().clearDriverCache().setup();
        driver = new ChromeDriver();
        LOGGER.info("-------------------Test name: Amortized Loan Pg Obj Data Provider--------------------");

        //Pulling the Url via config properties file
        String browserUrl = ReadConfigFiles.getPropertyValues("Url");
        ActOn.browser(driver).openBrowser(browserUrl);
    }

    @Test(dataProvider = "AmortizedLoanPgObjDataProvider", dataProviderClass = DataProviderClass.class)
    public void verifyAmortizedLoan(String loanAmount, String loanYearTerm, String loanMonthTerm, String interestRate, String quarterCom, String quarterPay, String finalQuarterAmount) {
        // 350000, 30, 1, 7, Quarterly, Every Quarter, $7348.44
        new LoanAmortized(driver)
                .clickOnLoanCalLink()
                .waitForPg()
                .typeLoanAmount(loanAmount)
                .typeLoanTermYear(loanYearTerm)
                .typeLoanTermMonth(loanMonthTerm)
                .typeInterestRate(interestRate)
                .selectQuarterlyComDropD(quarterCom)
                .selectQuarterlyPayDropD(quarterPay)
                .clickCalculate()
                .waitForPg()
                .verifyLoanPage()
                .verifyQuarterAmount(finalQuarterAmount);

    }

    @AfterMethod
    public void closeBrowser(){
        LOGGER.info("-------End Test Case: Amortized Loan Pg Obj Data Provider-------");
        ActOn.browser(driver).closeBrowser();
    }

}
