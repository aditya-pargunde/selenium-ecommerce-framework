package ecommerceautomation.pages;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import ecommerceautomation.utils.WaitUtils;

/**
 * Page Object Model for Login Page using locators.properties
 */
public class LoginPage {

    private WebDriver driver;
    private Properties locators;

    // Constructor
    public LoginPage(WebDriver driver) {
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

    // Element getters
    private WebElement signupLoginLink() {
        return driver.findElement(By.xpath(locators.getProperty("login.signupLoginLink")));
    }

    private WebElement emailInput() {
        return driver.findElement(By.xpath(locators.getProperty("login.emailInput")));
    }

    private WebElement passwordInput() {
        return driver.findElement(By.xpath(locators.getProperty("login.passwordInput")));
    }

    private WebElement loginButton() {
        return driver.findElement(By.xpath(locators.getProperty("login.loginButton")));
    }

    private WebElement loginErrorMessage() {
        return driver.findElement(By.xpath(locators.getProperty("login.loginErrorMessage")));
    }

    private WebElement logoutButton() {
        return driver.findElement(By.xpath(locators.getProperty("login.logoutButton")));
    }

    // Actions
    public void login(String email, String password) {
        clickOnSignupLoginHeader();
        enterEmailForLogin(email);
        enterPasswordForLogin(password);
        clickOnLoginButton();
    }

    public void clickOnSignupLoginHeader() {
        signupLoginLink().click();
    }

    public void enterEmailForLogin(String email) {
        WebElement element = emailInput();
        element.clear();
        element.sendKeys(email);
    }

    public void enterPasswordForLogin(String password) {
        WebElement element = passwordInput();
        element.clear();
        element.sendKeys(password);
    }

    public void clickOnLoginButton() {
        loginButton().click();
        WaitUtils wait = new WaitUtils(driver);
        wait.waitForPageToLoad(20);
    }

    public String getLoginErrorMessage() {
        return loginErrorMessage().getText();
    }

    public void logout() {
        logoutButton().click();
    }
}
