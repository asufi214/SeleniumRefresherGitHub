package automation_test.Calculator;

import command_providers.ActOn;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class PlayGround {

    WebDriver driver;

    @Test
    public void openBrowser() {
        //Open Chrome
        WebDriverManager.chromedriver().clearDriverCache().setup();
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        //Go to Calculate.net HP
        ActOn.browser(driver).openBrowser("https://www.calculator.net/");
    }

}
