package ecommerceautomation.pages;

import ecommerceautomation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class AccountInformationPage {

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

	public AccountInformationPage(WebDriver driver, WaitUtils wait) {
		this.driver = driver;
		this.wait = wait;
	}

	// ------------------- Locators -------------------
	private By passwordInput = By.xpath(locators.getProperty("account.passwordInputAcntInfo"));
	private By dayOfBirth = By.xpath(locators.getProperty("account.dayOfBirth"));
	private By monthOfBirth = By.xpath(locators.getProperty("account.monthOfBirth"));
	private By yearOfBirth = By.xpath(locators.getProperty("account.yearOfBirth"));
	private By titleRadioButtons = By.xpath("//input[@name='title']");
	private By firstName = By.xpath(locators.getProperty("account.firstName"));
	private By lastName = By.xpath(locators.getProperty("account.lastName"));
	private By companyName = By.xpath(locators.getProperty("account.companyName"));
	private By firstAddress = By.xpath(locators.getProperty("account.firstAddress"));
	private By countryName = By.xpath(locators.getProperty("account.countryName"));
	private By stateName = By.xpath(locators.getProperty("account.stateName"));
	private By cityName = By.xpath(locators.getProperty("account.cityName"));
	private By zipcode = By.xpath(locators.getProperty("account.zipcode"));
	private By mobileNumber = By.xpath(locators.getProperty("account.mobileNumber"));
	private By createAccountButton = By.xpath(locators.getProperty("account.createAccountButton"));

	// ------------------- Public Methods -------------------
	public void enterAccountInformation(String title, String password, String day, String month, String year) {
		selectTitle(title);
		wait.waitForElementToBeVisible(passwordInput).sendKeys(password);
		new Select(wait.waitForElementToBeVisible(dayOfBirth)).selectByValue(day);
		new Select(wait.waitForElementToBeVisible(monthOfBirth)).selectByVisibleText(month);
		new Select(wait.waitForElementToBeVisible(yearOfBirth)).selectByValue(year);
	}

	public void enterAddressInformation(String firstname, String lastname, String company, String address,
			String country, String state, String city, String zip, String mobileNo) {
		wait.waitForElementToBeVisible(firstName).sendKeys(firstname);
		wait.waitForElementToBeVisible(lastName).sendKeys(lastname);
		wait.waitForElementToBeVisible(companyName).sendKeys(company);
		wait.waitForElementToBeVisible(firstAddress).sendKeys(address);
		new Select(wait.waitForElementToBeVisible(countryName)).selectByValue(country);
		wait.waitForElementToBeVisible(stateName).sendKeys(state);
		wait.waitForElementToBeVisible(cityName).sendKeys(city);
		wait.waitForElementToBeVisible(zipcode).sendKeys(zip);
		wait.waitForElementToBeVisible(mobileNumber).sendKeys(mobileNo);
	}

	public HomePage clickOnCreateAccountButton() {
		wait.waitForElementToBeClickable(createAccountButton).click();
		return new HomePage(driver, wait);
	}

	private void selectTitle(String title) {
		List<WebElement> titles = wait.waitForAllElementsToBeVisible(titleRadioButtons);
		for (WebElement titleElement : titles) {
			if (titleElement.getAttribute("value").equalsIgnoreCase(title)) {
				titleElement.click();
				break;
			}
		}
	}
}