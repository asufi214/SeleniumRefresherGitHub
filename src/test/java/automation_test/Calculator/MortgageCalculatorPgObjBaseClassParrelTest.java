package automation_test.Calculator;


import org.testng.annotations.Test;
import page_objects.MortgageCalculator;
import automation_test.parallel.BaseClassUiTest;

// For parallel testing I'm using the DriverFactory with ThreadLocal.
// BaseClass adds another layer of reporting with each test case in parallel as opposed to sequential.
//  log4j2.xml was changed to the current one and the old sequential format is marked as log4j2_old.xlm
// TestNg.xml was also changed, so TCs can be done in parallel instead of sequentially, I can select more TC by increasing the Thread-Count.
public class MortgageCalculatorPgObjBaseClassParrelTest extends BaseClassUiTest {

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

}
