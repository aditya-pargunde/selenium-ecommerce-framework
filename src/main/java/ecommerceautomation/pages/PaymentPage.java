package ecommerceautomation.pages;

import ecommerceautomation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PaymentPage {
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

	public PaymentPage(WebDriver driver, WaitUtils wait) {
		this.driver = driver;
		this.wait = wait;
	}

	// ------------------- Locators -------------------
	private By nameOnCard = By.xpath(locators.getProperty("payment.nameOnCard"));
	private By cardNumber = By.xpath(locators.getProperty("payment.cardNumber"));
	private By cardCVNumber = By.xpath(locators.getProperty("payment.cardCVNumber"));
	private By cardExpiryMonth = By.xpath(locators.getProperty("payment.cardExpiryMonth"));
	private By cardExpiryYear = By.xpath(locators.getProperty("payment.cardExpiryYear"));
	private By PayAndConfirmOrderButton = By.xpath(locators.getProperty("payment.PayAndConfirmOrderButton"));

	// ------------------- Actions -------------------
	public void enterCardDetails(String name, String cardNumber, String CVCNumber,
			String expiryMonth, String expiryYear) {
		wait.waitForElementToBeVisible(nameOnCard).sendKeys(name);
		wait.waitForElementToBeVisible(this.cardNumber).sendKeys(cardNumber);
		wait.waitForElementToBeVisible(cardCVNumber).sendKeys(CVCNumber);
		wait.waitForElementToBeVisible(cardExpiryMonth).sendKeys(expiryMonth);
		wait.waitForElementToBeVisible(cardExpiryYear).sendKeys(expiryYear);
	}
	
	public OrderConfirmationPage confirmOrder() {
		wait.waitForElementToBeClickable(PayAndConfirmOrderButton).click();
		return new OrderConfirmationPage(driver, wait);
	}
}