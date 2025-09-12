package ecommerceautomation.pages;

import ecommerceautomation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CheckoutPage {

    private WebDriver driver;
    private WaitUtils wait;
    private Properties locators;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WaitUtils(driver, 10);
        locators = new Properties();
        try {
            FileInputStream fis = new FileInputStream("src/main/resources/locators.properties");
            locators.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load locators.properties file.");
        }
    }

    // 🔹 By Locators
    private By nameInput = By.xpath(locators.getProperty("order.nameInput"));
    private By addressInput = By.xpath(locators.getProperty("order.addressInput"));
    private By cityInput = By.xpath(locators.getProperty("order.cityInput"));
    private By zipInput = By.xpath(locators.getProperty("order.zipInput"));
    private By countryInput = By.xpath(locators.getProperty("order.countryInput"));
    private By placeOrderButton = By.xpath(locators.getProperty("order.placeOrderButton"));
    private By downloadInvoiceButton = By.xpath(locators.getProperty("order.downloadInvoiceButton"));

    // 🔹 Methods
    public void fillShipmentDetails(String name, String address, String city, String zip, String country) {
        wait.waitForElementToBeVisible(nameInput).sendKeys(name);
        wait.waitForElementToBeVisible(addressInput).sendKeys(address);
        wait.waitForElementToBeVisible(cityInput).sendKeys(city);
        wait.waitForElementToBeVisible(zipInput).sendKeys(zip);
        wait.waitForElementToBeVisible(countryInput).sendKeys(country);
    }

    public void placeOrder() {
        wait.waitForElementToBeClickable(placeOrderButton).click();
    }

    public void downloadInvoice() {
        wait.waitForElementToBeClickable(downloadInvoiceButton).click();
    }
}
