package command_providers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;

public class ElementActions {
    private static final Logger LOGGER = LogManager.getLogger(ElementActions.class);
    private By locator;
    WebDriver driver;

    public ElementActions(WebDriver driver, By locator) {
        this.driver = driver;
        this.locator = locator;
    }

    public WebElement getElement() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        WebElement element = null;
        try{
            element = driver.findElement(locator);
        } catch (Exception e) {
            LOGGER.error("Element Exception for the locator: " + locator + " and exception is: " + e.getMessage());
        }
        return element;
    }

    public ElementActions click() {
        getElement().click();
        return this;
    }

    public ElementActions setValue(String value) {
        getElement().clear();
        getElement().sendKeys(value);
        return this;
    }

    public ElementActions clear() {
        getElement().clear();
        return this;
    }

    //hardcoding backspace because clear failed on web element
    public ElementActions backspace() {
        WebElement we = driver.findElement(By.xpath("//input[@id='today2_Year_ID']"));
        String value = we.getAttribute("value");

        if (value != null) {
            int valLen = value.length();
            for (int i = 0; i < valLen; i++) {
                we.sendKeys(Keys.BACK_SPACE);
            }
        } return this;
    }

    public ElementActions selectValue(String value) {
        Select select = new Select(getElement());
        select.selectByVisibleText(value);
        return this;
    }

    public ElementActions mouseHover() {
        Actions actions = new Actions(driver);
        actions.moveToElement(getElement()).perform();
        return this;
    }

    public String getTextValue() {
        return getElement().getText();
    }

    public ElementActions scrollIntoView() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return this;
    }

    public ElementActions blankValue() {
        WebElement we = driver.findElement(By.xpath("//input[@id='today2_Year_ID']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value='';", we);
        return this;
    }
}
