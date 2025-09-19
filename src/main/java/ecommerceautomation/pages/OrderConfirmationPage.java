package ecommerceautomation.pages;

import ecommerceautomation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class OrderConfirmationPage {

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

	public OrderConfirmationPage(WebDriver driver, WaitUtils wait) {
		this.driver = driver;
		this.wait = wait;
	}

	// ------------------- Locators -------------------
	private By orderConfirmationMessage = By.xpath(locators.getProperty("confirm.confirmMessage"));
	private By downloadInvoiceButton = By.xpath(locators.getProperty("order.downloadInvoiceButton"));
	private By continueButton = By.xpath(locators.getProperty("confirm.continueButton"));

	// ------------------- Actions & Getters -------------------
	public String getOrderConfirmationMessage() {
		return wait.waitForElementToBeVisible(orderConfirmationMessage).getText();
	}

	public void downloadInvoice() {
		wait.waitForElementToBeClickable(downloadInvoiceButton).click();
	}

	// Click on continue to redirect to the home page
	public HomePage clickOnContinueButton() {
		wait.waitForElementToBeClickable(continueButton).click();
		return new HomePage(driver, wait);
	}
}