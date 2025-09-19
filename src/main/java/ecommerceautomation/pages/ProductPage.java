package ecommerceautomation.pages;

import ecommerceautomation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class ProductPage {

	private WebDriver driver;
	private WaitUtils wait;
	private static Properties locators;

	static {
		locators = new Properties();
		try (FileInputStream fis = new FileInputStream("src/main/resources/locators.properties")) {
			locators.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to load locators.properties file.");
		}
	}

	public ProductPage(WebDriver driver, WaitUtils wait) {
		this.driver = driver;
		this.wait = wait;
	}

	// ------------------- By Locators -------------------
	private By productHeader = By.xpath(locators.getProperty("product.productsLink"));
	private By searchBox = By.xpath(locators.getProperty("product.searchBox"));
	private By searchButton = By.xpath(locators.getProperty("product.searchButton"));
	private By productListLocator = By.xpath("//div[@class='productinfo text-center']");
	private By modalContent = By.xpath("//div[@class='modal-content']");
	private By viewCartLink = By.xpath("//a//u[text()='View Cart']");
	private By subscriptionText = By.xpath("//div//h2[text()='Subscription']");
	private By viewProductButton = By.xpath("//a[text()='View Product']");

	// ------------------- Public Methods -------------------
	public CartPage searchProduct(String productName) {
		wait.waitForElementToBeClickable(productHeader).click();
		performSearch(productName);
		WebElement productElement = findProductInList(productName);
		if (productElement != null) {
			addProductToCart(productElement);
		} else {
			throw new RuntimeException("Product '" + productName + "' not found on the page.");
		}
		return handleAddToCartModal();
	}

	// ------------------- Private Helper Methods -------------------
	private void performSearch(String productName) {
		wait.waitForElementToBeVisible(searchBox).sendKeys(productName);
		wait.waitForElementToBeClickable(searchButton).click();
	}

	private WebElement findProductInList(String productName) {
		List<WebElement> products = wait.waitForAllElementsToBeVisible(productListLocator);
		for (WebElement product : products) {
			WebElement searchedProductName = product.findElement(By.tagName("p"));
			if (searchedProductName.getText().equalsIgnoreCase(productName)) {
				return product;
			}
		}
		return null;
	}

	private void addProductToCart(WebElement productElement) {
		Actions actions = new Actions(driver);
		// scroll to view product button due to advertisement on page
		wait.scrollIntoView(viewProductButton);

		// Hover over the product container
		actions.moveToElement(productElement).perform();

		// Now, wait and find the 'Add to cart' button after hovering
		productElement.findElement(viewProductButton);
		WebElement addToCartButton = productElement.findElement(By.xpath(".//a[contains(text(),'Add to cart')]"));
		addToCartButton.click();
	}

	private CartPage handleAddToCartModal() {
		wait.waitForElementToBeVisible(modalContent);
		wait.waitForElementToBeClickable(viewCartLink).click();
		return new CartPage(driver, wait);
	}
}