package ecommerceautomation.tests;

import ecommerceautomation.base.BaseTest;
import ecommerceautomation.pages.SignupPage;
import ecommerceautomation.pages.AccountInformationPage;
import ecommerceautomation.pages.HomePage;
import ecommerceautomation.pages.LoginPage;
import ecommerceautomation.utils.ExcelUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SignupPageTest extends BaseTest {

	@DataProvider(name = "registerUserData")
	public Object[][] getRegisterUserData() throws Exception {
		ExcelUtils excel = new ExcelUtils(config.getProperty("user.data.path"), "RegisterUsers");
		return excel.getAllRowsAsObjectArray();
	}

	@Test(dataProvider = "registerUserData")
	public void registerNewUserTest(String name, String email, String password, String title, String day, String month,
			String year, String firstname, String lastname, String company, String address, String country,
			String state, String city, String zip, String mobileNo) {

		SignupPage signupPage = new SignupPage(getDriver(), wait);
		LoginPage loginPage = new LoginPage(getDriver(), wait);
		AccountInformationPage accountInfoPage = new AccountInformationPage(getDriver(), wait);
		HomePage homePage = new HomePage(getDriver(), wait);

		// click on signup header enter signup details
		signupPage.clickOnSignupLoginLink();
		signupPage.enterNameAtSignUp(name);
		signupPage.enterEmailAtSignUp(email);
		signupPage.clickOnSignupButton();

		// check if existing user
		boolean emailRegistered = signupPage.isEmailAlreadyRegistered();
		if (emailRegistered) {
			System.out.println("email already resgistered");
			Assert.assertTrue(emailRegistered, "Test failed: Email is already registered.");
			loginPage.enterEmailForLogin(email);
			loginPage.enterPasswordForLogin(password);
			loginPage.clickOnLoginButton();
			homePage.logout();
		} else {
			accountInfoPage.enterAccountInformation(title, password, day, month, year);
			accountInfoPage.enterAddressInformation(firstname, lastname, company, address, country, state, city, zip,
					mobileNo);
			homePage = accountInfoPage.clickOnCreateAccountButton();

			homePage.logout();
		}
	}
}