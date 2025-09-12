package ecommerceautomation.pages;

import ecommerceautomation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class CartPage {

    private WebDriver driver;
    private WaitUtils wait;
    private Properties locators;

    public CartPage(WebDriver driver) {
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
    private By productQuantity = By.xpath(locators.getProperty("cart.productQuantity"));
    private By deleteProduct = By.xpath(locators.getProperty("cart.deleteProduct"));
    private By allQuantities = By.xpath(locators.getProperty("cart.allQuantities"));
    private By allProductNames = By.xpath(locators.getProperty("cart.allProductNames"));
    private By checkoutButton= By.xpath(locators.getProperty("//a[normalize-space()='Proceed To Checkout']"));

    // 🔹 Methods
    
    public boolean isProductInCart(String productName) {
        for (WebElement product : driver.findElements(allProductNames)) {
            if (wait.waitForElementToBeVisible(product).getText().equalsIgnoreCase(productName)) {
                return true;
            }
        }
        return false;
    }
    public int getProductQuantity() {
        String quantity = wait.waitForElementToBeVisible(productQuantity).getText();
        return Integer.parseInt(quantity);
    }

    public void removeProduct() {
        wait.waitForElementToBeClickable(deleteProduct).click();
    }
    
    public void proceedToCheckout() {
    	wait.waitForElementToBeClickable(checkoutButton).click();
    }

    public List<Integer> getAllProductQuantities() {
        return driver.findElements(allQuantities).stream()
                .map(qty -> Integer.parseInt(wait.waitForElementToBeVisible(qty).getAttribute("value"))).collect(Collectors.toList());
    }

    public void removeProduct(String productName) {
        List<WebElement> names = driver.findElements(allProductNames);
        List<WebElement> deletes = driver.findElements(allDeleteButtons);

       for (int i = 0; i < names.size(); i++) {
           if (wait.waitForElementToBeVisible(names.get(i)).getText().equalsIgnoreCase(productName)) {
               wait.waitForElementToBeClickable(deletes.get(i)).click();
               break;
           }
        }
    }
}
