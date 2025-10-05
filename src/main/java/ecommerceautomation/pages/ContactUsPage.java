package ecommerceautomation.pages;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import ecommerceautomation.utils.WaitUtils;

public class ContactUsPage {
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

	public ContactUsPage(WebDriver driver, WaitUtils wait) {
		this.driver = driver;
		this.wait = wait;
	}

	// ------------------- Locators -------------------
	private By contactForm = By.xpath(locators.getProperty("contact.ContactForm"));
	private By getInTouch = By.xpath(locators.getProperty("contact.GetInTouch"));
	private By cotactFormName = By.xpath(locators.getProperty("contact.Name"));
	private By contactFormEmail = By.xpath(locators.getProperty("contact.Email"));
	private By contactFormSubject = By.xpath(locators.getProperty("contact.Subject"));
	private By contactFormMessage = By.xpath(locators.getProperty("contact.Message"));
	private By contactFormFileUploadButton = By.xpath(locators.getProperty("contact.FileUpload"));
	private By contactFormSubmitButton = By.xpath(locators.getProperty("contact.SubmitButton"));
	

	// ------------------- Methods -------------------
	
	public void isContactFormVisible() {
		wait.waitForElementToBeVisible(contactForm);
		wait.waitForAllElementsToBeVisible(getInTouch);
		WebElement getInTouchMessage = driver.findElement(By.xpath(locators.getProperty("contact.GetInTouch")));
		Assert.assertEquals(getInTouchMessage.getText(), "GET IN TOUCH");
	}
	public void enterContactFormDetails(String name, String email, String subject, String message) {
		wait.waitForElementToBeVisible(contactForm);
		wait.waitForElementToBeClickable(cotactFormName).sendKeys(name);
		wait.waitForElementToBeClickable(contactFormEmail).sendKeys(email);
		wait.waitForElementToBeClickable(contactFormSubject).sendKeys(subject);
		wait.waitForElementToBeClickable(contactFormMessage).sendKeys(message);
	}

	public void uploadContactFormFile() {
		wait.waitForAllElementsToBeVisible(contactFormFileUploadButton);
		WebElement FileUploadButton = driver.findElement(By.xpath(locators.getProperty("contact.FileUpload")));
		FileUploadButton.sendKeys("/Users/adityapargunde/Downloads/Manual Testing Qna.pdf");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.attributeContains(FileUploadButton, "value", "Manual Testing Qna.pdf"));
	}

	public void contactFileUploadSubmit() {
		wait.waitForElementToBeClickable(contactFormSubmitButton).click();
		Alert alert = driver.switchTo().alert();
		alert.accept();
	}
	
	public void ContactFormSubmitSuccess() {
		WebElement contactFormSuccessMessage= driver.findElement(By.xpath(locators.getProperty("contact.ContactFormSuccess")));
		Assert.assertEquals(contactFormSuccessMessage.getText(), "Success! Your details have been submitted successfully.");
	}
}
