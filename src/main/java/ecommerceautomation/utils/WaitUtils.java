package ecommerceautomation.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import ecommerceautomation.base.ConfigReader;

import java.time.Duration;

public class WaitUtils {

	private WebDriver driver;
	private WebDriverWait wait;

	public WaitUtils(WebDriver driver) {
		this.driver = driver;
		int explicitWait = ConfigReader.getInt("explicit.wait", 15);
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));
	}

	public void waitForPageToLoad(long timeoutInSeconds) {
		wait(driver, Duration.ofSeconds(timeoutInSeconds))
				.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState")
						.equals("complete"));
	}

	public WebElement waitForElementToBeVisible(By productQuantity) {
		return wait.until(ExpectedConditions.visibilityOf((WebElement) productQuantity));
	}

	public WebElement waitForElementToBeClickable(By deleteProduct) {
		return wait.until(ExpectedConditions.elementToBeClickable(deleteProduct));
	}

	public boolean waitForTextToBePresent(WebElement element, String text) {
		return wait.until(ExpectedConditions.textToBePresentInElement(element, text));
	}
}
