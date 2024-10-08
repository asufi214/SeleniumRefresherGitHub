package calculator;

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
        driver = new ChromeDriver();
        //Go to Calculate.net HP
        driver.get("https://www.calculator.net/");
        //Maximize the webpage
        driver.manage().window().maximize();
        //Click on Loan Calculator
        driver.findElement(LoanCalLink).click();
    }

    public void enterData(){
        //Enter 350k loan amount
        driver.findElement(LoanAmount).clear();
        driver.findElement(LoanAmount).sendKeys("350000");
        //Enter 30 Year & 1 month for loan amount
        driver.findElement((LoanTerm)).clear();
        driver.findElement(LoanTerm).sendKeys("30");
        driver.findElement(LoanMth).sendKeys("1");
        //Enter Interest rate of 7.5
        driver.findElement(InterestRate).clear();
        driver.findElement(InterestRate).sendKeys("7.5");
        //Select Quarterly from compound dropdown
        Select select = new Select(driver.findElement(CompoundDrop));
        select.selectByVisibleText("Quarterly");
        //Select Every Quarter from Payback dropdown
        Select select1 = new Select(driver.findElement(PayBackTerm));
        select1.selectByVisibleText("Every Quarter");
        //Click on calculate button
        driver.findElement(CalculateLoan).click();
    }

    @Test
    public void VerifyAmortizedLoanFunction(){
        //Call enterData actions before the test functions
        enterData();

        //Get page title
        String expectedPage = "Loan Calculator";
        String actualPage = driver.getTitle();
        //Validate user landed on Loan Calculator page
        Assert.assertEquals(actualPage,expectedPage);

        //Validate Quarterly repayment amount of $7348.44
        String expectedQrtPayment = "$7,348.44";
        String formattedXpath = String.format("/html/body/div[3]/div[1]/div[3]/table/tbody/tr[1]/td[2]/b[text()='$7,348.44']");
        boolean present = driver.findElement(By.xpath(formattedXpath)).isDisplayed();
    }

    @AfterMethod
    public void closeBrowser(){
    //    driver.quit();
    }
}
