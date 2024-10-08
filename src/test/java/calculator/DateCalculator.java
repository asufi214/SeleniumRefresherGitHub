package calculator;

import command_providers.ActOn;
import command_providers.AssertThat;
import command_providers.WaitFor;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utlities.DateUtils;

public class DateCalculator {

    private static By DateCalLink = By.linkText("Date Calculator");
    private static By DateCalTitle = By.xpath("//*[@id=\"content\"]/h1[text()='Date Calculator']");
    private static By CorrectFrame = By.xpath("//*[@id=\"content\"]/div[3]/form");
    private static By RelatedField = By.xpath("//*[@id=\"content\"]/fieldset/legend[text()='Related']");
    private static By MonthDropDown = By.id("today2_Month_ID");
    private static By DayDropDown = By.id("today2_Day_ID");
    private static By YearField = By.xpath("//input[@id='today2_Year_ID']");
            //By.id("today2_Year_ID");
    private static By AddSubtractDropDown = By.name("c2op");
    private static By AddYearField = By.name("c2year");
    private static By AddMonthField = By.name("c2month");
    private static By AddWeekField = By.name("c2week");
    private static By AddDayField = By.name("c2day");
    private static By CalculateButton = By.xpath("//*[@id=\"content\"]/div[3]/form/table/tbody/tr[3]/td[2]/input[2]/..");
    private static By ResultVerify = By.xpath("//*[@id=\"content\"]/h2[3][text()='Result']");

    WebDriver driver;

    @BeforeMethod
    public void openBrowser() {
        //Setup Chrome & Launch browser
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        //Navigate to Calculator.net
        ActOn.browser(driver).openBrowser("https://www.calculator.net/");

        ActOn.wait(driver, DateCalLink).waitForElementToBeVisible();

        //Navigate to Date Calculator page
        ActOn.element(driver, DateCalLink).click();
    }

    public void enterData() {
        //Verify User lands on Calculator page - pull title: Date Calculator
        ActOn.wait(driver, DateCalTitle).waitForElementToBeVisible();

        //Using DateUtils to always return an advanced month/year to use in automation so user doesn't need to update those fields
        String date = DateUtils.returnNextMonth();
        String[] dates = date.split("-");
        String month = dates[0];
        String day = dates[1];
        String year = dates[1];
        System.out.println();

        //Scroll correct frame/related field into view & adding wait for pg load
        ActOn.element(driver, RelatedField).scrollIntoView();
        ActOn.wait(driver, CorrectFrame).waitForElementToBeVisible();

        //Select month from Dropdown
        ActOn.element(driver, MonthDropDown).selectValue("Dec");
        //Select day from Dropdown
        ActOn.element(driver, DayDropDown).selectValue("27");
        //Hard Clear failed so hardcoded backspace then set year
        ActOn.element(driver, YearField).clear();

        //Using the YearField xpath & making a WebElement to pull the value of the field
        WebElement we = driver.findElement(By.xpath("//input[@id='today2_Year_ID']"));
        String value = we.getAttribute("value");

        //Using Keys to use back_space to delete the preset year in the Year field
        if (value !=null) {
            int valLen = value.length();
             for(int i = 0; i < valLen; i++) {
                 we.sendKeys(Keys.BACK_SPACE);
             }
        }

        //Using the ElementAction of backspace for this field
        ActOn.element(driver, YearField).backspace();
        //Using the ElementAction & JavascriptExecutor for this field to manually set it to blank value
        ActOn.element(driver, YearField).blankValue().setValue("2025");
        //Add 1 for add year field
        ActOn.element(driver, AddYearField).setValue("1");
        //Add 2 for add month field
        ActOn.element(driver, AddMonthField).setValue("2");
        //Add 3 for add weeks field
        ActOn.element(driver, AddWeekField).setValue("3");
        //Add 15 for add day field
        ActOn.element(driver, AddDayField).setValue("15");
        //Click on Calculate button
        ActOn.element(driver, CalculateButton).click();
    }

    @Test
    public void verifyDayCal() {
        //Entering all necessary Data
        enterData();
        //Setting a wait for our Result field to come to view
        ActOn.wait(driver, ResultVerify).waitForElementToBeVisible();

        //Verify expected 'Result' field
        AssertThat.elementAssertions(driver, ResultVerify).elementIsDisplayed();

    }

    @AfterMethod
    public void closeBrowser() {
        ActOn.browser(driver).closeBrowser();
    }

}
