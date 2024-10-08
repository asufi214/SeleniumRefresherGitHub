package sauceDemo;

import command_providers.ActOn;
import command_providers.AssertThat;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginPage {

    private final static By UserNameField = By.id("user-name");
    private final static By PasswordField = By.id("password");
    private final static By LoginButton = By.id("login-button");
    private final static By SortDropDown = By.xpath("//*[@id=\"header_container\"]/div[2]/div/span/select");
    private final static By SortByPrice = By.xpath("//*[@id=\"header_container\"]/div[2]/div/span/select/..select class[3]");
    private final static By SauceOneSie = By.linkText("Sauce Labs Onesie");
    private final static By AddToCartButton = By.id("add-to-cart-sauce-labs-onesie");
    private final static By RemoveItemButton = By.id("remove-sauce-labs-onesie");
    private final static By ShopingCart = By.xpath("//*[@id=\"shopping_cart_container\"]/a");
    private final static By CheckOutButton = By.id("checkout");
    private final static By FirstName = By.id("first-name");
    private final static By LastName = By.id("last-name");
    private final static By ZipCode = By.id("postal-code");
    private final static By ContinueButton = By.id("continue");
    private final static By CreditCard = By.xpath("//div/div[2]/div[2][text()='SauceCard #31337']");
    private final static By PonyExpress = By.xpath("//div/div[2]/div[4][text()='Free Pony Express Delivery!']");
    private final static By FinishButton = By.id("finish");

    WebDriver driver;

    @BeforeMethod

    public void openBrowser() {
        //Select Chrome & launch the app
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        //Navigate to Saucedemo.com
        ActOn.browser(driver).openBrowser("https://www.saucedemo.com/");
    }

    public void enterLogin() {
        //Enter Username 'standard_user' & password 'secret_sauce'
        ActOn.element(driver, UserNameField).setValue("standard_user");
        ActOn.element(driver, PasswordField).setValue("secret_sauce");
        //Click Login
        ActOn.element(driver, LoginButton).click();
    }

    public void changeItemSort() {
        //Wait for Inventory page to load & dropdown is visable
        ActOn.wait(driver, SortDropDown).waitForElementToBeVisible();
        //Change the 'Name (A-Z)' dropdown to 'Price (low to high)'
        ActOn.element(driver, SortDropDown).selectValue("Price (low to high)");
    }

    public void selectItem() {

        //Click on Sauce Onesie when it becomes available
        ActOn.wait(driver, SauceOneSie).waitForElementToBeVisible();
        ActOn.element(driver, SauceOneSie).click();

        //Add Sauce Onsie item to cart
        ActOn.element(driver, AddToCartButton).click();

        //Verify 'Remove' button is present after pressing addToCartButton before navigating to cart
        ActOn.wait(driver, RemoveItemButton).waitForElementToBeVisible();
    }

    public void navigateCheckOut(){

        //Navigate to Shopping Cart
        ActOn.element(driver, ShopingCart).click();

        //Checkout Item
        ActOn.element(driver, CheckOutButton).click();
    }

    public void enterContactInfo() {
        //Enter First (John) & Last (Smith) Name & Zip Code (11232)
        ActOn.element(driver, FirstName).setValue("John");
        ActOn.element(driver, LastName).setValue("Smith");
        ActOn.element(driver, ZipCode).setValue("11232");

        //Click on Continue
        ActOn.element(driver, ContinueButton).click();
    }

    @Test
    public void verifyOrder() {

        //Enter Login info & Sort Item
        enterLogin();
        changeItemSort();
        selectItem();
        navigateCheckOut();
        enterContactInfo();

        //Verify 'Free Pony Express Delivery!'
     //   String PulledShipping = ActOn.element(driver, PonyExpress).getTextValue();
     //   Assert.assertEquals(PulledShipping, "Free Pony Express Delivery!");

        String expectedPullShipping = "Free Pony Express Delivery!";
        String formattedXpath = String.format("//div/div[2]/div[4][text()='Free Pony Express Delivery!']", expectedPullShipping);

        By endShipping = By.xpath(formattedXpath);
        //Validate the Shipping is: Free Pony Express Delivery!
        AssertThat.elementAssertions(driver, endShipping).elementIsDisplayed();

        //Verify Credit card is 'SauceCard #31337'
        String expectedCard = "SauceCard #31337";
        String cardFormattedXpath = String.format("//div/div[2]/div[2][text()='SauceCard #31337']", expectedCard);

        By finalCard = By.xpath(cardFormattedXpath);
        AssertThat.elementAssertions(driver, finalCard).elementIsDisplayed();

        //Click on Finish Button
        ActOn.element(driver, FinishButton).click();

        //Verify order complete message after finish button is clicked: Thank you for your order!
        String OrderComplete = "Thank you for your order!";
        String OrderFormattedXpath = String.format("//h2[text()='Thank you for your order!']", OrderComplete);

        By OrderThanks = By.xpath(OrderFormattedXpath);

        AssertThat.elementAssertions(driver, OrderThanks).elementIsDisplayed();

    }

    @AfterMethod
    public void closeBrowser() {
        //Close browser tab
        ActOn.browser(driver).closeBrowser();
    }
}
