package ecommerceautomation.pages;

import ecommerceautomation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.Arrays;

public class CheckoutPage {

	private WebDriver driver;
	private WaitUtils wait;
	private WebDriverWait waitDriver;
	private static Properties locators;
	JavascriptExecutor js = (JavascriptExecutor) driver;

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

	public CheckoutPage(WebDriver driver, WaitUtils wait) {
		this.driver = driver;
		this.wait = wait;
	}

	// ------------------- Locators -------------------
	private By deliveryName = By.xpath(locators.getProperty("order.deliveryName"));
	private By deliveryCompany = By.xpath(locators.getProperty("order.deliveryCompany"));
	private By deliveryAddress = By.xpath(locators.getProperty("order.deliveryAddress"));
	private By deliveryCityStateZip = By.xpath(locators.getProperty("order.deliveryCityStateZip"));
	private By deliveryCountry = By.xpath(locators.getProperty("order.deliveryCountry"));
	private By deliveryPhone = By.xpath(locators.getProperty("order.deliveryPhone"));
	private By billingName = By.xpath(locators.getProperty("order.billingName"));
	private By billingCompany = By.xpath(locators.getProperty("order.billingCompany"));
	private By billingAddress = By.xpath(locators.getProperty("order.billingAddress"));
	private By billingCityStateZip = By.xpath(locators.getProperty("order.billingCityStateZip"));
	private By billingCountry = By.xpath(locators.getProperty("order.billingCountry"));
	private By billingPhone = By.xpath(locators.getProperty("order.billingPhone"));

	// ------------------- Public Methods -------------------
	public List<String> getDeliveryAddressDetails() {
		String dName = wait.waitForElementToBeVisible(deliveryName).getText();
		String dCompany = wait.waitForElementToBeVisible(deliveryCompany).getText();
		String dAddress = wait.waitForElementToBeVisible(deliveryAddress).getText();
		String dCityStateZip = wait.waitForElementToBeVisible(deliveryCityStateZip).getText();
		String dCountry = wait.waitForElementToBeVisible(deliveryCountry).getText();
		String dPhone = wait.waitForElementToBeVisible(deliveryPhone).getText();

		return Arrays.asList(dName, dCompany, dAddress, dCityStateZip, dCountry, dPhone);
	}

	public List<String> getBillingAddressDetails() {
		String bName = wait.waitForElementToBeVisible(billingName).getText();
		String bCompany = wait.waitForElementToBeVisible(billingCompany).getText();
		String bAddress = wait.waitForElementToBeVisible(billingAddress).getText();
		String bCityStateZip = wait.waitForElementToBeVisible(billingCityStateZip).getText();
		String bCountry = wait.waitForElementToBeVisible(billingCountry).getText();
		String bPhone = wait.waitForElementToBeVisible(billingPhone).getText();

		return Arrays.asList(bName, bCompany, bAddress, bCityStateZip, bCountry, bPhone);
	}

	public PaymentPage placeOrder() {
		WebDriverWait waitDriver = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement placeOrderButton = waitDriver
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@href='/payment']")));

		// Scroll to the place order button as it is not visible.
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", placeOrderButton);

		// Wait until the placeorder button clickable and then click
		waitDriver.until(ExpectedConditions.elementToBeClickable(placeOrderButton));
		placeOrderButton.click();

		return new PaymentPage(driver, wait);
	}
}