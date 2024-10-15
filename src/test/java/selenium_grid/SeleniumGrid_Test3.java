package selenium_grid;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;
import utlities.DriverFactory;

import java.net.MalformedURLException;
import java.net.URL;

public class SeleniumGrid_Test3 {

    @Test
    public void executeInAwsDocker() {
   //     FirefoxOptions firefoxOptions = new FirefoxOptions();
   //     URL gridUrl = new URL("http://34.230.37.132:4444/wd/hub");
        WebDriver driver = DriverFactory.getInstance().getDriver();
        driver.get("https://www.yahoo.com/");
        System.out.println(driver.getTitle());
        driver.quit();
    }
}
