package ecommerceautomation.tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import ecommerceautomation.base.BaseTest;
import ecommerceautomation.listeners.ExtentTestNGListener;
import ecommerceautomation.pages.ContactUsPage;
import ecommerceautomation.pages.HomePage;
import ecommerceautomation.utils.ExcelUtils;
import ecommerceautomation.utils.WaitUtils;

@Listeners(ExtentTestNGListener.class)
public class ContactUsPageTest extends BaseTest {

	@DataProvider(name = "contactPageData")
	public Object[][] contactPageData() throws Exception {
		ExcelUtils excel = new ExcelUtils(config.getProperty("user.data.path"), "ValidLoginUsers");
		return excel.getAllRowsAsObjectArray();
	}

	@Test(dataProvider = "contactPageData", groups = { "sanity" })
	public void contactPageTest(String name, String email, String password, String title, String day, String month,
			String year, String firstname, String lastname, String company, String address, String country,
			String state, String city, String zip, String mobileNo, String subject, String message) {

		HomePage homePage = new HomePage(getDriver(), wait);
		ContactUsPage contactPage = new ContactUsPage(getDriver(), wait);
		homePage.ClickOnContactUs();
		contactPage.enterContactFormDetails(name, email, subject, message);
		contactPage.uploadContactFormFile();
		contactPage.contactFileUploadSubmit();
		contactPage.ContactFormSubmitSuccess();
	}
}
