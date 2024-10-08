package automation_test.parallel;

import org.testng.annotations.Test;
import page_objects.LoanAmortized;
import utlities.RetryFailedTests;

// For parallel testing I'm using the DriverFactory with ThreadLocal.
// BaseClass adds another layer of reporting with each test case in parallel as opposed to sequential.
//  log4j2.xml was changed to the current one and the old sequential format is marked as log4j2_old.xlm
// TestNg.xml was also changed, so TCs can be done in parallel instead of sequentially, I can select more TC by increasing the Thread-Count.
public class AmortizedLoanPgObjBaseClassParrelTest extends BaseClassUiTest {

    @Test(retryAnalyzer = RetryFailedTests.class)
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
