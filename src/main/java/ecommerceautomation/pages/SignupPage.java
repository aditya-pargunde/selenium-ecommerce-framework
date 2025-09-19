package ecommerceautomation.pages;

import ecommerceautomation.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class SignupPage {

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

    public SignupPage(WebDriver driver, WaitUtils wait) {
        this.driver = driver;
        this.wait = wait;
    }

    // ------------------- Locators -------------------
    private By signupLoginLink = By.xpath(locators.getProperty("login.signupLoginLink"));
    private By nameInputForSignup = By.xpath(locators.getProperty("signup.signupName"));
    private By emailInputForSignup = By.xpath(locators.getProperty("signup.signupEmail"));
    private By signupButton = By.xpath(locators.getProperty("signup.signupButton"));
    private By emailExistsMessage = By.xpath(locators.getProperty("signup.signupEmailExistMessage"));

    // ------------------- Public Methods -------------------
    public void clickOnSignupLoginLink() {
        wait.waitForElementToBeClickable(signupLoginLink).click();
    }

    public void enterNameAtSignUp(String name) {
        wait.waitForElementToBeVisible(nameInputForSignup).sendKeys(name);
    }

    public void enterEmailAtSignUp(String email) {
        wait.waitForElementToBeVisible(emailInputForSignup).sendKeys(email);
    }

    public boolean isEmailAlreadyRegistered() {
        return wait.isElementVisible(emailExistsMessage, 10);
    }

    public AccountInformationPage clickOnSignupButton() {
        wait.waitForElementToBeClickable(signupButton).click();
        return new AccountInformationPage(driver, wait);
    }
}