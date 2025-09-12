package ecommerceautomation.pages;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import ecommerceautomation.utils.ExcelUtils;
import junit.framework.Assert;

/**
 * Page Object Model for User Registration
 */
public class RegisterUserPage {

	private WebDriver driver;
	private Properties locators;
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	public RegisterUserPage(WebDriver driver) {
		this.driver = driver;
		locators = new Properties();
		try {
			FileInputStream fis = new FileInputStream("src/main/resources/locators.properties");
			locators.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to load locators.properties file.");
		}
	}

	// Locators getters
	private WebElement signupLoginLink() {
		return driver.findElement(By.xpath(locators.getProperty("account.signupLoginLink")));
	}

	private WebElement nameInputForSignup() {
		return driver.findElement(By.xpath(locators.getProperty("account.nameInputAcntInfo")));
	}

	private WebElement emailInputForSignup() {
		return driver.findElement(By.xpath(locators.getProperty("account.emailInputAcntInfo")));
	}

	private WebElement signupButton() {
		return driver.findElement(By.xpath(locators.getProperty("account.signupButton")));
	}

	private WebElement emailExistsMessage() {
		return driver.findElement(By.xpath(locators.getProperty("account.emailExistsMessage")));
	}

	private WebElement nameInputAcntInfo() {
		return driver.findElement(By.xpath(locators.getProperty("account.nameInputAcntInfo")));
	}

	private WebElement emailInputAcntInfo() {
		return driver.findElement(By.xpath(locators.getProperty("account.emailInputAcntInfo")));
	}

	private WebElement passwordInputAcntInfo() {
		return driver.findElement(By.xpath(locators.getProperty("account.passwordInputAcntInfo")));
	}

	private WebElement dayOfBirth() {
		return driver.findElement(By.xpath(locators.getProperty("account.dayOfBirth")));
	}

	private WebElement monthOfBirth() {
		return driver.findElement(By.xpath(locators.getProperty("account.monthOfBirth")));
	}

	private WebElement yearOfBirth() {
		return driver.findElement(By.xpath(locators.getProperty("account.yearOfBirth")));
	}

	private WebElement firstName() {
		return driver.findElement(By.xpath(locators.getProperty("account.firstName")));
	}

	private WebElement lastName() {
		return driver.findElement(By.xpath(locators.getProperty("account.lastName")));
	}

	private WebElement companyName() {
		return driver.findElement(By.xpath(locators.getProperty("account.companyName")));
	}

	private WebElement firstAdress() {
		return driver.findElement(By.xpath(locators.getProperty("account.firstAddress")));
	}

	private WebElement countryName() {
		return driver.findElement(By.xpath(locators.getProperty("account.countryName")));
	}

	private WebElement stateName() {
		return driver.findElement(By.xpath(locators.getProperty("account.stateName")));
	}

	private WebElement cityName() {
		return driver.findElement(By.xpath(locators.getProperty("account.cityName")));
	}

	private WebElement zipcode() {
		return driver.findElement(By.xpath(locators.getProperty("account.zipcode")));
	}

	private WebElement mobileNumber() {
		return driver.findElement(By.xpath(locators.getProperty("account.mobileNumber")));
	}

	private WebElement createAccountButton() {
		return driver.findElement(By.xpath(locators.getProperty("account.createAccountButton")));
	}

	private WebElement accountCreationSuccessMessage() {
		return driver.findElement(By.xpath(locators.getProperty("account.accountCreationSuccessMessage")));
	}

	private WebElement continueButton() {
		return driver.findElement(By.xpath(locators.getProperty("account.continueButton")));
	}

	// Actions
	public void clickOnSignupLoginLink() {
		signupLoginLink().click();
	}

	public void registerUser(String name, String email) {
		clickOnSignupLoginLink();
		enterNameAtSignUp(name);
		enterEmailAtSignUp(email);
		clickOnSignupButton();
	}

	public void enterAccountInformation(String title, String name, String email, String password, String date,
			String month, String year) {
		selectTitle(title);
		verifyNameAtAcntInfo(name);
		verifyEmailAtAcntInfo(email);
		enterPassword(password);
		selectBirthDate(date);
		selectBirthMonth(month);
		selectBirthYear(year);
	}

	public void enterAddressInformation(String firstname, String lastname, String country, String state, String city,
			String zip, String mobileNo, String company, String address) {
		enterFirstName(firstname);
		enterLastName(lastname);
		enterCompany(company);
		enterAddress(address);
		selectCountry(country);
		enterState(state);
		enterCity(city);
		enterZipcode(zip);
		enterMobileNumber(mobileNo);
		clickOnCreateAccountButton();
	}

	public void clickOnCreateAccountButton() {
		createAccountButton().click();
	}

	public void clickOnContinue() {
		Assert.assertEquals("Congratulations! Your new account has been successfully created!",
				accountCreationSuccessMessage().getText());
		continueButton().click();
	}

	public void enterNameAtSignUp(String name) {
		nameInputForSignup().clear();
		nameInputForSignup().sendKeys(name);
	}

	public void enterEmailAtSignUp(String email) {
		emailInputForSignup().clear();
		emailInputForSignup().sendKeys(email);
	}

	public void selectTitle(String title) {
		WebElement radioBtn = driver.findElement(By.xpath("//input[@name='title' and @value='" + title + "']"));
		if (!radioBtn.isSelected()) {
			radioBtn.click();
		}
	}

	public void verifyNameAtAcntInfo(String name) {
		String nameValue = nameInputAcntInfo().getAttribute("value");
		Assert.assertEquals(name, nameValue);
	}

	public void verifyEmailAtAcntInfo(String email) {
		String emailValue = emailInputAcntInfo().getAttribute("value");
		Assert.assertEquals(email, emailValue);
	}

	private void enterPassword(String password) {
		WebElement element = passwordInputAcntInfo();
		element.sendKeys(password);
	}

	public void selectBirthDate(String date) {
		Select select = new Select(dayOfBirth());
		select.selectByValue(date);
	}

	public void selectBirthMonth(String month) {
		Select select = new Select(monthOfBirth());
		select.selectByValue(month);
	}

	public void selectBirthYear(String year) {
		Select select = new Select(yearOfBirth());
		select.selectByValue(year);
	}

	public void enterFirstName(String name) {
		firstName().sendKeys(name);
	}

	public void enterLastName(String lastname) {
		lastName().sendKeys(lastname);
	}

	public void enterCompany(String company) {
		companyName().sendKeys(company);
	}

	private void enterAddress(String address) {
		firstAdress().sendKeys(address);
	}

	public void selectCountry(String country) {
		Select select = new Select(countryName());
		select.selectByValue(country);
	}

	private void enterState(String state) {
		stateName().sendKeys(state);
	}

	private void enterCity(String city) {
		cityName().sendKeys(city);
	}

	private void enterZipcode(String zip) {
		zipcode().sendKeys(zip);
	}

	private void enterMobileNumber(String mobile) {
		mobileNumber().sendKeys(mobile);
	}

	public void clickOnSignupButton() {
		signupButton().click();
	}

	public String getEmailExistsMessage() {
		return emailExistsMessage().getText();
	}
}
