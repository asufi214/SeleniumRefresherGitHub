package automation_test.parallel;

import command_providers.ActOn;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import utlities.DriverFactory;

public class BaseClassUiTest {
    public WebDriver driver;
    Logger LOGGER = LogManager.getLogger(this.getClass().getName());
    String testCaseName = String.format("------Test: %s-----------", this.getClass().getName());
    String endTestCase = String.format("-------Test End: %s---------------", this.getClass().getName());

    @BeforeClass
    public void addThread() {
        ThreadContext.put("threadName", this.getClass().getName());
        driver = DriverFactory.getInstance().getDriver();
    // Using DriverFactory now instead of ChromeDriver so we can call firefox, and other browsers in parallel testing.
    }

    @BeforeMethod
    public void browserInitialization() {
        LOGGER.info(testCaseName);
        ActOn.browser(driver).openBrowser("https://www.calculator.net/");
    }

    @AfterMethod
    public void quitBrowser() {
        DriverFactory.getInstance().removeDriver();
        LOGGER.info(endTestCase);
    }
}
