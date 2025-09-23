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

public class SignupSteps {

    private SignupPage signupPage;
    private HomePage homePage;
    private LoginPage loginPage;
    private AccountInformationPage accountInfoPage;
    private WaitUtils wait;

    public SignupSteps() {
        if (BaseTest.getDriver() == null) {
            WebDriver driver = DriverManager.initDriver("chrome", false);
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(180));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            driver.get(ecommerceautomation.base.ConfigReader.get("baseURL"));
        }

        this.wait = new WaitUtils(BaseTest.getDriver());
        this.signupPage = new SignupPage(BaseTest.getDriver(), wait);
        this.homePage = new HomePage(BaseTest.getDriver(), wait);
        this.loginPage = new LoginPage(BaseTest.getDriver(), wait);
    }

    @Given("User is on the signup Page")
    public void user_is_on_the_signup_page() {
        loginPage.clickOnSignupLoginLink();
    }

    @Then("User enters name {string} email {string} title {string} password {string} Day {string} Month {string} Year {string} firstname {string} lastname {string} company {string} Address {string} Country {string} State {string} City {string} Zipcode {string} MobileNo {string}")
    public void user_enters_credentials(String name, String email, String title, String password, String day, String month, String year,
                                        String firstName, String lastName, String company, String address, String country, String state,
                                        String city, String zipcode, String mobilenumber) {
    	 if (accountInfoPage == null) {
    	        accountInfoPage = new AccountInformationPage(BaseTest.getDriver(), wait);
    	    }
        signupPage.enterNameAtSignUp(firstName);
        signupPage.enterEmailAtSignUp(email);
        signupPage.clickOnSignupButton();
        accountInfoPage.enterAccountInformation(title, password, day, month, year);
        accountInfoPage.enterAddressInformation(firstName, lastName, company, address, country, state, city, zipcode, mobilenumber);
    }

    @And("User clicks on create Account")
    public void user_clicks_on_create_account() {
        accountInfoPage.clickOnCreateAccountButton();
    }

    @Then("User should see account creation success message")
    public void user_should_see_account_creation_success_message() {
        accountInfoPage.accountCreationConfirmation();
    }

    @Then("User clicks on the continue button")
    public void user_clicks_on_the_continue_button() {
        accountInfoPage.clickOnContinueButton();
    }

    @Then("sees home page")
    public void sees_home_page() {
        homePage.isUserLoggedIn();
    }

    @Then("user logs out")
    public void user_logs_out() {
        homePage.logout();
    }
}