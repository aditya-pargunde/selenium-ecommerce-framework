package ecommerceautomation.stepdefinitions;

import java.time.Duration;

import org.openqa.selenium.WebDriver;

import ecommerceautomation.base.BaseTest;
import ecommerceautomation.base.DriverManager;
import ecommerceautomation.pages.AccountInformationPage;
import ecommerceautomation.pages.HomePage;
import ecommerceautomation.pages.LoginPage;
import ecommerceautomation.pages.SignupPage;
import ecommerceautomation.utils.WaitUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DeleteAccountSteps {

	private LoginPage loginPage;
	private SignupPage signupPage;
	private HomePage homePage;
	private AccountInformationPage accountInfoPage;
	private WaitUtils wait;

	public DeleteAccountSteps() {
		if (BaseTest.getDriver() == null) {
			WebDriver driver = DriverManager.initDriver("chrome", false);
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(180));
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			driver.get(ecommerceautomation.base.ConfigReader.get("baseURL"));
		}
		this.wait = new WaitUtils(BaseTest.getDriver());
		this.loginPage = new LoginPage(BaseTest.getDriver(), wait);
		this.signupPage = new SignupPage(BaseTest.getDriver(), wait);
		this.homePage = new HomePage(BaseTest.getDriver(), wait);
	}

	@Given("User lands on the signup Page")
	public void userLandsOnTheSignupPage() {
		loginPage.clickOnSignupLoginLink();
	}

	@When("just click on delete account")
	public void userClicksOnDeleteAcntButton() {
		homePage.deleteAccount();
	}

	@Then("verify account delete message")
	public void verifyDeleteMessage() {
		homePage.verifyAccountDeletedMessage();
	}
	
	@And("click on continue")
	public void clickOnContinue() {
		homePage.clickOnContinueAfterDeletion();
	}

}
