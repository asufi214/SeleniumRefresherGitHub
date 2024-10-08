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
import page_objects.MortgageCalculator;
import parameters.DataProviderClass;
import utlities.ReadConfigFiles;

public class MortgageCalculatorDataProvider {
    private static final Logger LOGGER = LogManager.getLogger(MortgageCalculatorDataProvider.class);
    WebDriver driver;

    @BeforeMethod
    public void openBrowser() {
        WebDriverManager.chromedriver().clearDriverCache().setup();
        driver = new ChromeDriver();
        LOGGER.info("---------------------Test Name: Calculate Monthly Payment DataProvider---------------------");

        String browserUrl = ReadConfigFiles.getPropertyValues("Url");
        ActOn.browser(driver).openBrowser(browserUrl);

    }

    @Test(dataProvider = "MortgageCalculatorDataProvider", dataProviderClass = DataProviderClass.class)
    public void verifyMortgagePage(String homePrice, String dollarDrop, String downPayment, String loanTerm, String interestRate, String startDateMonth, String startDateYear, String selectPropertyTax, String propertyTax, String homeInsurance, String pmiInsurance, String enterPmi, String totalMonthPay, String totalMonthlyPayment) throws InterruptedException {
        //"350000","$","70000","30","9","Jun","2024","%","7","15000","%","3","$2,252.94","$2,252.94"
        new MortgageCalculator(driver)
                .clickMortgageLink()
                .verifyMortgagePage()
                .clearMortgagePage()
                .enterHomeValue(homePrice)
                .selectDollarDrop(dollarDrop)
                .enterDownPayment(downPayment)
                .enterLoanTerm(loanTerm)
                .enterInterestRate(interestRate)
                .selectMonth(startDateMonth)
                .enterYear(startDateYear)
                .selectPercentPropertyTax(selectPropertyTax)
                .enterPropertyTax(propertyTax)
                .enterHomeInsurance(homeInsurance)
                .selectPmiPercent(pmiInsurance)
                .enterPmi(enterPmi)
                .clickCalculate()
                .verifyRate(totalMonthPay)
                .verifyRate2(totalMonthlyPayment);

    }

    @AfterMethod
    public void closeBrowser(){
        LOGGER.info("---------------------End Test Case: Calculate Monthly Payment DataProvider---------------------");
        ActOn.browser(driver).closeBrowser();
    }
}
