package selenium_grid;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.Test;
import utlities.DriverFactory;

public class SeleniumGrid_Test4 {
    @Test
    public void executeInAwsDocker() {

        //     EdgeOptions edgeOptions = new EdgeOptions();
        //     URL gridUrl = new URL("http://http://3.83.216.192:4444/wd/hub");
        WebDriver driver = DriverFactory.getInstance().getDriver();
        driver.get("https://www.yahoo.com/");
        System.out.println(driver.getTitle());
        driver.quit();
    }
}
