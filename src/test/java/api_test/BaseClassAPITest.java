package api_test;

import command_providers.ActOn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import utlities.DriverFactory;

public class BaseClassAPITest {
    public WebDriver driver;
    Logger LOGGER = LogManager.getLogger(this.getClass().getName());
    String testCaseName = String.format("------Test: %s-----------", this.getClass().getName());
    String endTestCase = String.format("-------Test End: %s---------------", this.getClass().getName());

    @BeforeClass
    public void addThread() {
        ThreadContext.put("threadName", this.getClass().getName());
    //    WebDriverManager.chromedriver().setup();
    //    driver = new ChromeDriver();
    // Using DriverFactory now instead of ChromeDriver so we can call firefox, and other browsers in parallel testing.
   //     driver = DriverFactory.getInstance().getDriver();
    }
}
