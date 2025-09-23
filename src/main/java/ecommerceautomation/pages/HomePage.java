package ecommerceautomation.pages;

import ecommerceautomation.utils.WaitUtils;
import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class HomePage {

	private WebDriver driver;
	private WaitUtils wait;
	private static Properties locators;
	HomePage homePage;

	static {
		locators = new Properties();
		try (FileInputStream fis = new FileInputStream("src/main/resources/locators.properties")) {
			locators.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to load locators.properties file.");
		}
	}

	public HomePage(WebDriver driver, WaitUtils wait) {
		this.driver = driver;
		this.wait = wait;
	}

	// ------------------- Locators -------------------
	private By logoutButton = By.xpath(locators.getProperty("home.logoutButton"));
	private By productsLink = By.xpath(locators.getProperty("home.productsLink"));
	private By deleteAccountLink = By.xpath(locators.getProperty("home.deleteAccountLink"));
	private By AccountDeletionSuccessMessage= By.xpath(locators.getProperty("home.AccountDeletionSuccessMessage"));
	private By continueAfterDeletionBtn = By.xpath(locators.getProperty("account.continueButton"));

	// ------------------- Methods -------------------
	public boolean isUserLoggedIn() {
		return wait.isElementVisible(logoutButton, 10);
	}

	public LoginPage logout() {
		if (isUserLoggedIn()) {
			wait.waitForElementToBeClickable(logoutButton).click();
			return new LoginPage(driver, wait);
		}
		return null;
	}

	public ProductPage navigateToProductsPage() {
		wait.waitForElementToBeClickable(productsLink).click();
		return new ProductPage(driver, wait);
	}
	
	public HomePage deleteAccount() {
		wait.waitForAllElementsToBeVisible(deleteAccountLink);
		wait.waitForElementToBeClickable(deleteAccountLink).click();
		return new HomePage(driver,wait);
	}
	
	public void clickOnContinueAfterDeletion() {
        wait.waitForElementToBeVisible(continueAfterDeletionBtn);
        wait.waitForElementToBeClickable(continueAfterDeletionBtn);
    }
	
	public HomePage verifyAccountDeletedMessage() {
		WebElement DeletionSuccessMessage = driver.findElement(By.xpath(locators.getProperty("home.AccountDeletionSuccessMessage")));
		Assert.assertEquals(DeletionSuccessMessage.getText(), "ACCOUNT DELETED!");
		System.out.print(DeletionSuccessMessage.getText());
		return new HomePage(driver,wait);
	}
}