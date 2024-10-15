package automation_test.parallel;

import command_providers.ActOn;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import page_objects.LoanAmortized;

public class AmortizedLoanPgObjParrelTest  extends BaseClassUiTest {

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
//    WebDriver driver;

    @Test
    public void verifyAmortizedLoan(){
        new LoanAmortized(driver)
                .clickOnLoanCalLink()
                .typeLoanAmount("350000")
                .typeLoanTermYear("30")
                .typeLoanTermMonth("1")
                .typeInterestRate("7.5")
                .selectQuarterlyComDropD("Quarterly")
                .selectQuarterlyPayDropD("Every Quarter")
                .clickCalculate()
                .waitForPg()
                .verifyLoanPage()
                .verifyQuarterAmount("$7348.44");

    }
}
