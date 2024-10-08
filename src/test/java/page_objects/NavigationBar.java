package page_objects;

import command_providers.ActOn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationBar {
    private final static By CollectiblesLink = By.linkText("Collectibles");
    private final static By PokemonLink = By.linkText("Pokemon");
    private final By ElectronicsTab = By.linkText("Electronics");
    private final By RefurbishedLink = By.linkText("eBay Refurbished");
    private final By HomePage = By.id("gh-la");

    private static final Logger LOGGER = LogManager.getLogger(NavigationBar.class);

    public WebDriver driver;

    public NavigationBar(WebDriver driver) {
        this.driver = driver;
    }

    public NavigationBar mouseHoverToCollectibles() {
        LOGGER.debug("Mouse hover to Collectibles Link");
        //Hover mouse to Collectables so dropdown option Pokémon is available
        ActOn.element(driver, CollectiblesLink).mouseHover();
        return this;
    }

    public Pokemon navigateToPokemon() {
        LOGGER.debug("Clicking Pokemon page link");
        //Setting a wait time so Pokemon Link can be present
        ActOn.wait(driver, PokemonLink).waitForElementToBeVisible();
        //Click on Pokemon
        ActOn.element(driver, PokemonLink).click();
        return new Pokemon(driver);
    }


    public NavigationBar mouseHoverToElectronics() {
        LOGGER.debug("mouse hover to Electronics page");
        //Hover mouse to Electronics so dropdown option Refurbished is available
        ActOn.element(driver, ElectronicsTab).mouseHover();
        return this;
    }

    public Refurbished navigateToRefurbished() {
        LOGGER.debug("Navigating to Refurbished page");
        //Setting a wait time so Refurbished Link can be present
        ActOn.wait(driver, RefurbishedLink).waitForElementToBeVisible();
        //Click on Ebay Refurbished Link
        ActOn.element(driver, RefurbishedLink).click();
        return new Refurbished();
    }

    public Home navigateToHome() {
        LOGGER.debug("Navigating back to home page");
        //Click on Ebay home icon to Return to home
        ActOn.element(driver, HomePage).click();
        return new Home(driver);
    }

}
