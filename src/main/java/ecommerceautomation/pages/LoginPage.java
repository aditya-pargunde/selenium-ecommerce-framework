package ecommerceautomation.pages;

import ecommerceautomation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LoginPage {

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

	public LoginPage(WebDriver driver, WaitUtils wait) {
		this.driver = driver;
		this.wait = wait;
	}

	// ------------------- By Locators -------------------
	private By signupLoginLink = By.xpath(locators.getProperty("login.signupLoginLink"));
	private By emailInput = By.xpath(locators.getProperty("login.emailInput"));
	private By passwordInput = By.xpath(locators.getProperty("login.passwordInput"));
	private By loginButton = By.xpath(locators.getProperty("login.loginButton"));
	private By loginErrorMessage = By.xpath(locators.getProperty("login.loginErrorMessage"));
	private By logoutButton = By.xpath(locators.getProperty("home.logoutButton"));
	private By loginAccountText = By.xpath(locators.getProperty("login.loginToAccountMessage"));
	
	
	// ------------------- Public Methods -------------------
	public void clickOnSignupLoginLink() {
		wait.waitForElementToBeClickable(signupLoginLink).click();
	}
	
	public void isLoginTextVisible() {
		WebElement loginAcntMessage = driver.findElement(By.xpath(locators.getProperty("login.loginToAccountMessage")));
		Assert.assertEquals(loginAcntMessage.getText(), "Login to your account");
	}

	public void enterEmailForLogin(String email) {
		WebElement emailElement = wait.waitForElementToBeVisible(emailInput);
		emailElement.clear();
		emailElement.sendKeys(email);
	}

	public void enterPasswordForLogin(String password) {
		WebElement passwordElement = wait.waitForElementToBeVisible(passwordInput);
		passwordElement.clear();
		passwordElement.sendKeys(password);
	}

	public void clickOnLoginButton() {
		wait.waitForElementToBeClickable(loginButton).click();
	}

	public boolean isLoginButtonVisible() {
		try {
			wait.waitForElementToBeVisible(loginButton);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isSignupLoginLinkVisible() {
		return wait.isElementVisible(signupLoginLink, 5);
	}

	public String getLoginErrorMessage() {
		if (wait.isElementVisible(loginErrorMessage, 3)) {
			return wait.waitForElementToBeVisible(loginErrorMessage).getText();
		}
		return null;
	}

	public Object login(String email, String password) {
		clickOnSignupLoginLink();
		enterEmailForLogin(email);
		enterPasswordForLogin(password);
		clickOnLoginButton();

		// Check if logout button is visible on the next/home page
		if (wait.isElementVisible(logoutButton, 5)) {
			return new HomePage(driver, wait);
		} else {
			return new LoginPage(driver, wait);
		}
		
	}

	public Object loginFromCurrentPage(String email, String password) {
		enterEmailForLogin(email);
		enterPasswordForLogin(password);
		clickOnLoginButton();

		// Check for a unique web element on the Home Page to determine success
		if (wait.isElementVisible(logoutButton, 5)) {
			return new HomePage(driver, wait);
		} else {
			return new LoginPage(driver, wait);
		}
	}

	public void logout() {
		if (wait.isElementVisible(logoutButton, 10)) {
			wait.waitForElementToBeClickable(logoutButton).click();
		}
	}

	public boolean isLoginSuccessful() {
		return wait.isElementVisible(logoutButton, 10);
	}
}