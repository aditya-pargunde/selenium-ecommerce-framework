package ecommerceautomation.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ecommerceautomation.base.BaseTest;
import ecommerceautomation.pages.LoginPage;
import ecommerceautomation.pages.SignupPage;
import ecommerceautomation.utils.ExcelUtils;

//Test Case 5: Register User with existing email
//1. Launch browser
//2. Navigate to url 'http://automationexercise.com'
//3. Verify that home page is visible successfully
//4. Click on 'Signup / Login' button
//5. Verify 'New User Signup!' is visible
//6. Enter name and already registered email address
//7. Click 'Signup' button
//8. Verify error 'Email Address already exist!' is visible

public class TestCaseFive extends BaseTest {
	@DataProvider(name = "ExistingUserData")
	public Object[][] getUserLogoutData() throws Exception {
		ExcelUtils excel = new ExcelUtils(config.getProperty("user.data.path"), "UserLogout");
		return excel.getAllRowsAsObjectArray();
	}

	@Test(dataProvider = "ExistingUserData", groups = { "TestCases" })
	public void registerExistingUserTestFive(String name, String email, String password) {

		// Step 1,2,and 3 launch browser and go to home page.
		SignupPage signupPage = new SignupPage(getDriver(), wait);
		LoginPage loginPage = new LoginPage(getDriver(), wait);

		// Step 4 and 5: click on Signup/Login and verify sign up text
		signupPage.clickOnSignupLoginLink();
		signupPage.isSignupTextVisible();
		
		//Step 6: enter aleady registered name and email and click on signu
		signupPage.enterNameAtSignUp(name);
		signupPage.enterEmailAtSignUp(email);
		signupPage.clickOnSignupButton();
		
		//Step 7:Verify error 'Email Address already exist!' is visible
		signupPage.isEmailAlreadyRegistered();		
	}
}
