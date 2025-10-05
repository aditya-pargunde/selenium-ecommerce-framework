package ecommerceautomation.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ecommerceautomation.base.BaseTest;
import ecommerceautomation.pages.ContactUsPage;
import ecommerceautomation.pages.HomePage;
import ecommerceautomation.pages.LoginPage;
import ecommerceautomation.pages.SignupPage;
import ecommerceautomation.utils.ExcelUtils;


//Test Case 6: Contact Us Form
//1. Launch browser
//2. Navigate to url 'http://automationexercise.com'
//3. Verify that home page is visible successfully
//4. Click on 'Contact Us' button
//5. Verify 'GET IN TOUCH' is visible
//6. Enter name, email, subject and message
//7. Upload file
//8. Click 'Submit' button
//9. Click OK button
//10. Verify success message 'Success! Your details have been submitted successfully.' is visible
//11. Click 'Home' button and verify that landed to home page successfully

public class TestCaseSix extends BaseTest{
	@DataProvider(name = "ContactUsData")
	public Object[][] getContactData() throws Exception {
		ExcelUtils excel = new ExcelUtils(config.getProperty("user.data.path"), "ValidLoginUsers");
		return excel.getAllRowsAsObjectArray();
	}

	@Test(dataProvider = "ContactUsData", groups = { "TestCases" })
	public void ContactUsTestSix(String name, String email, String password, String title, String day, String month,
			String year, String firstname, String lastname, String company, String address, String country,
			String state, String city, String zip, String mobileNo, String subject, String message) {

		// Step 1,2,and 3 launch browser and go to home page.
		HomePage homePage = new HomePage(getDriver(), wait);	
		ContactUsPage contactPage = new ContactUsPage(getDriver(), wait);
		
		//Step 4 and 5: click on contact us button and verify 'GET IN TOUCH' is visible
		homePage.ClickOnContactUs();
		contactPage.isContactFormVisible();
		
		//Step 6. Enter name, email, subject and message
		contactPage.enterContactFormDetails(lastname, email, subject, message);
		
		//Step 7,8 and 9: upload file, click submit and click on alert
		contactPage.uploadContactFormFile();
		contactPage.contactFileUploadSubmit();
		
		//10. Verify success message and  go to home page
		contactPage.ContactFormSubmitSuccess();
		homePage.navigateToHomePage();
	}
}
