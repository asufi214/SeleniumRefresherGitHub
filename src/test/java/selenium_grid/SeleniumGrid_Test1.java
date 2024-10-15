package selenium_grid;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;
import utlities.DriverFactory;

import java.net.MalformedURLException;
import java.net.URL;

public class SeleniumGrid_Test1 {

    @Test
    public void executeInAwsDocker() {
        //       ChromeOptions chromeOptions = new ChromeOptions();
        //       URL gridUrl = new URL("http://34.230.37.132:4444/wd/hub");
        WebDriver driver = DriverFactory.getInstance().getDriver();
        driver.get("https://www.calculator.net/");
        System.out.println(driver.getTitle());
        driver.quit();
    }
}
