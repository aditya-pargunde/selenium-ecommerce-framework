package ecommerceautomation.pages;

import ecommerceautomation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.stream.Collectors;

public class CartPage {

	private WebDriver driver;
	private WaitUtils wait;
	private static Properties locators;

	// Static block to load properties file only once
	static {
		locators = new Properties();
		try (FileInputStream fis = new FileInputStream("src/main/resources/locators.properties")) {
			locators.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to load locators.properties file.");
		}
	}

	public CartPage(WebDriver driver, WaitUtils wait) {
		this.driver = driver;
		this.wait = wait;
	}

	// ------------------- Locator Methods -------------------
	// Use By objects for reusability and explicit waits
	private By productQuantity = By.xpath(locators.getProperty("cart.productQuantity"));
	private By deleteButtons = By.xpath(locators.getProperty("cart.deleteButtons"));
	private By allQuantities = By.xpath(locators.getProperty("cart.allQuantities"));
	private By allProductNames = By.xpath(locators.getProperty("cart.allProductNames"));
	private By checkoutButton = By.xpath(locators.getProperty("cart.checkoutButton"));
	private By checkoutModal = By.xpath(locators.getProperty("cart.checkoutModal"));
	private By registerLoginButtonAtModal = By.xpath(locators.getProperty("cart.RegisterLoginButtonAtModal"));
	private By cartPageLink = By.xpath("//a[@href='/view_cart']");

	// ------------------- Public Methods -------------------
	public boolean isProductInCart(String productName) {
		List<WebElement> products = wait.waitForAllElementsToBeVisible(allProductNames);
		return products.stream().anyMatch(product -> product.getText().equalsIgnoreCase(productName));
	}

	public int getProductQuantity() {
		String quantity = wait.waitForElementToBeVisible(productQuantity).getText();
		return Integer.parseInt(quantity);
	}

	public void removeProduct(String productName) {
		// Wait for all product names to be visible.
		List<WebElement> names = wait.waitForAllElementsToBeVisible(allProductNames);

		// Find the index of the product you want to remove
		int productIndex = -1;
		for (int i = 0; i < names.size(); i++) {
			if (names.get(i).getText().equalsIgnoreCase(productName)) {
				productIndex = i;
				break;
			}
		}

		if (productIndex != -1) {
			// Construct the locator for the specific delete button using the index
			String deleteButtonXpath = locators.getProperty("cart.deleteButtons") + "[" + (productIndex + 1) + "]";
			By deleteButtonLocator = By.xpath(deleteButtonXpath);

			// Pass the By locator to the WaitUtils method
			wait.waitForElementToBeClickable(deleteButtonLocator).click();
		} else {
			throw new NoSuchElementException("Product '" + productName + "' not found in the cart.");
		}
	}

	public CheckoutPage proceedToCheckout() {
		wait.waitForElementToBeClickable(checkoutButton).click();

		// Handle modal if it appears incase user is not logged in
		if (wait.isElementVisible(checkoutModal, 3)) {
			wait.waitForElementToBeClickable(registerLoginButtonAtModal).click();
		}
		return new CheckoutPage(driver, wait);
	}

	public List<Integer> getAllProductQuantities() {
		return wait.waitForAllElementsToBeVisible(allQuantities).stream()
				.map(qty -> Integer.parseInt(qty.getAttribute("value"))).collect(Collectors.toList());
	}

	public CartPage cartPageLink() {
		wait.waitForElementToBeClickable(cartPageLink).click();
		return new CartPage(driver, wait);
	}
}