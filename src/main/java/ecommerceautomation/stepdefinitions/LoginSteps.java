package ecommerceautomation.stepdefinitions;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import ecommerceautomation.base.BaseTest;
import ecommerceautomation.base.DriverManager;
import ecommerceautomation.pages.HomePage;
import ecommerceautomation.pages.LoginPage;
import ecommerceautomation.utils.WaitUtils;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSteps {

	private LoginPage loginPage;
	private HomePage homePage;
	private WaitUtils wait;

	public LoginSteps() {
		if (BaseTest.getDriver() == null) {
			WebDriver driver = DriverManager.initDriver("chrome", false);
			driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(180));
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			driver.get(ecommerceautomation.base.ConfigReader.get("baseURL")); // fixed
		}
		this.wait = new WaitUtils(BaseTest.getDriver());
		this.loginPage = new LoginPage(BaseTest.getDriver(), wait);
		this.homePage = new HomePage(BaseTest.getDriver(), wait);
	}

	@Given("User is on the Login Page")
	public void user_on_login_page() {
		// Lazy initialization ensures driver is ready
		wait = new WaitUtils(BaseTest.getDriver());
		loginPage = new LoginPage(BaseTest.getDriver(), wait);
		homePage = new HomePage(BaseTest.getDriver(), wait);

		loginPage.clickOnSignupLoginLink();
	}

	@When("User enters email {string} and password {string}")
	public void user_enters_credentials(String email, String password) {
		loginPage.enterEmailForLogin(email);
		loginPage.enterPasswordForLogin(password);
	}

	@And("Clicks on the login button")
	public void clicks_login_button() {
		loginPage.clickOnLoginButton();
	}

	@Then("User should see the homepage")
	public void user_should_see_homepage() {
		homePage.isUserLoggedIn();
	}

	@Then("User should log out")
	public void user_should_log_out() {
		loginPage.logout();
	}

	@Then("User should see a login error message")
	public void invalidLoginErrorMessage() {
		loginPage.getLoginErrorMessage();
	}

}
