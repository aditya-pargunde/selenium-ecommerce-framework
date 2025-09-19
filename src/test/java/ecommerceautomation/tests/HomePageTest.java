package ecommerceautomation.tests;

import ecommerceautomation.base.BaseTest;
import ecommerceautomation.pages.HomePage;
import ecommerceautomation.pages.ProductPage;
import ecommerceautomation.utils.ExcelUtils;
import ecommerceautomation.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class HomePageTest extends BaseTest {

	@DataProvider(name = "ValidLoginData")
	public Object[][] getValidLoginData() throws Exception {
		ExcelUtils excel = new ExcelUtils(config.getProperty("user.data.path"), "ValidLoginUsers");
		return excel.getAllRowsAsObjectArray();
	}

	// This is working fine
	@Test(dataProvider = "ValidLoginData")
	public void verifyUserIsLoggedIn(String name, String email, String password) {
		// login to app first
		LoginPage loginPage = new LoginPage(getDriver(), wait);
		loginPage.login(email, password);

		// Step 2: Instantiate the HomePage object
		HomePage homePage = new HomePage(getDriver(), wait);

		// Step 3: Assert that the user is logged in
		Assert.assertTrue(homePage.isUserLoggedIn(),
				"User should be logged in and the 'logout button should be visible.");
	}

	@Test
	public void navigateToProductsPage() {
		// Step 1: Start on the home page
		HomePage homePage = new HomePage(getDriver(), wait);

		// Step 2: Navigate to the products page using the HomePage method
		ProductPage productPage = homePage.navigateToProductsPage();

		// Step 3: Assert that the URL has changed to the products page URL
		Assert.assertTrue(getDriver().getCurrentUrl().contains("products"),
				"URL should contain 'products' after navigating to the products page.");
	}

	@Test(dataProvider = "ValidLoginData")
	public void verifyLogoutFunctionality(String name, String email, String password) {
		// Step 1: Login with valid user
		LoginPage loginPage = new LoginPage(getDriver(), wait);
		loginPage.login(email, password);

		HomePage homePage = new HomePage(getDriver(), wait);

		// Step 2: Perform the logout action
		loginPage = homePage.logout();

		// Step 3: Assert that the user has been logged out by checking for a
		// login-related element
		Assert.assertTrue(loginPage.isLoginButtonVisible(),
				"After logout, the signup / login button should be visible.");
	}
}