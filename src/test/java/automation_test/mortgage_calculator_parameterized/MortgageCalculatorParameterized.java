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
import utlities.ReadConfigFiles;
import utlities.SqlConnector;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MortgageCalculatorParameterized {
    private static final Logger LOGGER = LogManager.getLogger(MortgageCalculatorParameterized.class);

    WebDriver driver;

    @BeforeMethod
    public void openBrowser() {

        //Open Chrome
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        LOGGER.info("---------------------Test Name: Calculate Monthly Payment pgAdmin---------------------");


        String browserUrl = ReadConfigFiles.getPropertyValues("Url");
        ActOn.browser(driver).openBrowser(browserUrl);

    }

    @Test
    public void verifyMortgagePage(){

        try {
            ResultSet rs = SqlConnector.readData("select * from monthly_mortgage");
            while (rs.next()) {
                new MortgageCalculator(driver)
                        .clickMortgageLink()
                        .clearMortgagePage()
                        .enterHomeValue(rs.getString("homeprice"))
                        .selectDollarDrop(rs.getString("dollarydrop"))
                        .enterDownPayment(rs.getString("downpayment"))
                        .enterLoanTerm(rs.getString("loanterm"))
                        .enterInterestRate(rs.getString("interestrate"))
                        .selectMonth(rs.getString("startdatemonth"))
                        .enterYear(rs.getString("startdateyear"))
                        .selectPercentPropertyTax(rs.getString("selectpropertytax"))
                        .enterPropertyTax(rs.getString("propertytax"))
                        .enterHomeInsurance(rs.getString("homeinsurance"))
                        .selectPmiPercent(rs.getString("pmiinsurance"))
                        .enterPmi(rs.getString("enterpmi"))
                        .clickCalculate()
                        .verifyRate("$2,252.94")
                        .verifyRate2(rs.getString("totalmonthlypayment"));
            }
        } catch (SQLException | InterruptedException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @AfterMethod
    public void closeBrowser(){
        LOGGER.info("--------------------- End Test Case---------------------");
        ActOn.browser(driver).closeBrowser();
    }
}
