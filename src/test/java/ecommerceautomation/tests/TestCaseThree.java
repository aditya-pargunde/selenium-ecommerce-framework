package ecommerceautomation.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ecommerceautomation.base.BaseTest;
import ecommerceautomation.pages.HomePage;
import ecommerceautomation.pages.LoginPage;
import ecommerceautomation.pages.SignupPage;
import ecommerceautomation.utils.ExcelUtils;

//Test Case 3: Login User with incorrect email and password
//1. Launch browser
//2. Navigate to url 'http://automationexercise.com'
//3. Verify that home page is visible successfully
//4. Click on 'Signup / Login' button
//5. Verify 'Login to your account' is visible
//6. Enter incorrect email address and password
//7. Click 'login' button
//8. Verify error 'Your email or password is incorrect!' is visible

public class TestCaseThree extends BaseTest {
	@DataProvider(name = "invalidLoginData")
	public Object[][] getValidUserLoginData() throws Exception {
		ExcelUtils excel = new ExcelUtils(config.getProperty("user.data.path"), "InvalidLoginUsers");
		return excel.getAllRowsAsObjectArray();
	}

	@Test(dataProvider = "invalidLoginData", groups = { "TestCases" })
	public void InvalidUserLoginTestThree(String name, String email, String password) {

		// Step 1,2,and 3 launch browser and go to home page.
		SignupPage signupPage = new SignupPage(getDriver(), wait);
		LoginPage loginPage = new LoginPage(getDriver(), wait);

		// Step 4 and 5: click on Signup/Login and verify login text
		signupPage.clickOnSignupLoginLink();
		loginPage.isLoginTextVisible();

		// Step 6 and 7: Enter incorrect email, password and click login
		loginPage.enterEmailForLogin(email);
		loginPage.enterPasswordForLogin(password);
		loginPage.clickOnLoginButton();

		// Step 8: Verify error 'Your email or password is incorrect!' is visible
		loginPage.getLoginErrorMessage();
	}
}
