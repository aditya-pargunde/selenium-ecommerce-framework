package ecommerceautomation.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ecommerceautomation.base.BaseTest;
import ecommerceautomation.pages.AccountInformationPage;
import ecommerceautomation.pages.HomePage;
import ecommerceautomation.pages.SignupPage;
import ecommerceautomation.utils.ExcelUtils;

//Test Case 1: Register User
//1. Launch browser
//2. Navigate to url 'http://automationexercise.com'
//3. Verify that home page is visible successfully
//4. Click on 'Signup / Login' button
//5. Verify 'New User Signup!' is visible
//6. Enter name and email address
//7. Click 'Signup' button
//8. Verify that 'ENTER ACCOUNT INFORMATION' is visible
//9. Fill details: Title, Name, Email, Password, Date of birth
//10. Fill details: First name, Last name, Company, Address, Address2, Country, State, City, Zipcode, Mobile Number
//11. Click 'Create Account button'
//12. Verify that 'ACCOUNT CREATED!' is visible
//13. Click 'Continue' button
//14. Verify that 'Logged in as username' is visible
//15. Click 'Delete Account' button
//16. Verify that 'ACCOUNT DELETED!' is visible and click 'Continue' button

public class TestCaseOne extends BaseTest{
	@DataProvider(name = "registerUserData")
	public Object[][] getRegisterUserData() throws Exception {
		ExcelUtils excel = new ExcelUtils(config.getProperty("user.data.path"), "RegisterUsers");
		return excel.getAllRowsAsObjectArray();
	}
	@Test(dataProvider = "registerUserData", groups= {"TestCases"})
	public void RegisterUserTestOne(String name, String email, String password, String title, String day, String month,
			String year, String firstname, String lastname, String company, String address, String country,
			String state, String city, String zip, String mobileNo, String subject, String message) {

		// Step 1,2,and 3 launch browser and go to home page.
		SignupPage signupPage = new SignupPage(getDriver(), wait);
		
		//Step 4 and 5:click on Signup/Login and verify sign up text
		signupPage.clickOnSignupLoginLink();
		
		// Step 6 and 7: Enter name and email address and click on signup button
		signupPage.enterNameAtSignUp(name);
		signupPage.enterEmailAtSignUp(email);
		AccountInformationPage accountInfoPage = signupPage.clickOnSignupButton();

		// Step 8, 9 and 10: Verify 'ENTER ACCOUNT INFORMATION' text and enter details
		accountInfoPage.enterAccountInformation(title, password, day, month, year);
		accountInfoPage.enterAddressInformation(firstname, lastname, company, address, country, state, city, zip,
				mobileNo);

		// Step 11,12 and 13: Click create account and navigate to the home page
		HomePage homePage = accountInfoPage.clickOnCreateAccountButton();
		accountInfoPage.accountCreationConfirmation();
		accountInfoPage.clickOnContinueButton();

		// Step 14: Verify user is logged in
		Assert.assertTrue(homePage.isUserLoggedIn(), "Account creation failed or user not logged in.");
		
		//Step 15 and 16: Delete account and confirm deletion message and click on continue
		homePage.deleteAccount();
		homePage.verifyAccountDeletedMessage();
		homePage.clickOnContinueAfterDeletion();
	}
}
