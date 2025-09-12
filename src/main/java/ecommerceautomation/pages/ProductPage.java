package ecommerceautomation.pages;

import ecommerceautomation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class ProductPage {

    private WebDriver driver;
    private WaitUtils wait;
    private Properties locators;

    public ProductPage(WebDriver driver) {
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

    // Locators getters  login.productsLink
    
    private By productHeader = By.xpath(locators.getProperty("login.productsLink"));
    private By searchBox = By.xpath(locators.getProperty("product.searchBox"));
    private By searchButton = By.xpath(locators.getProperty("product.searchButton"));
    private By addToCartButton = By.xpath(locators.getProperty("product.addToCartButton"));
    private By productListLocator = By.xpath("//div[@class='productinfo text-center']");
    private By modalContent = By.xpath("//div[@class='modal-content']");
    private By viewCartLink = By.xpath("//a//u[text()='View Cart']");

    // Methods
    public void searchProduct(String productName) {
        // Step 1: Click products link
        wait.waitForElementToBeClickable(productHeader).click();

        // Step 2: Enter product name
        wait.waitForElementToBeVisible(searchBox).sendKeys(productName);
        wait.waitForElementToBeClickable(searchButton).click();

        // Step 3: Get product list
        List<WebElement> productList = driver.findElements(productListLocator);
        for (WebElement product : productList) {
            WebElement searchedProductName = product.findElement(By.tagName("p"));
            System.out.println("Product: " + searchedProductName.getText());

            if (searchedProductName.getText().equalsIgnoreCase(productName)) {
                // Hover on product
                Actions actions = new Actions(driver);
                actions.moveToElement(searchedProductName).perform();

                // click on Add to cart
                wait.waitForElementToBeClickable(addToCartButton).click();

                // Wait for modal & click "View Cart"
                wait.waitForElementToBeVisible(modalContent);
                wait.waitForElementToBeClickable(viewCartLink).click();
                break; // Stop loop once matched product is handled
            }
        }
    }

//    public void addFirstProductToCart() {
//        wait.waitForElementToBeClickable(addToCartButton()).click();
//    }
}
