package ecommerceautomation.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ecommerceautomation.base.BaseTest;
import ecommerceautomation.pages.CartPage;
import ecommerceautomation.pages.HomePage;
import ecommerceautomation.pages.LoginPage;
import ecommerceautomation.pages.ProductPage;
import ecommerceautomation.pages.SignupPage;
import ecommerceautomation.utils.ExcelUtils;

//Test Case 9: Search Product
//1. Launch browser
//2. Navigate to url 'http://automationexercise.com'
//3. Verify that home page is visible successfully
//4. Click on 'Products' button
//5. Verify user is navigated to ALL PRODUCTS page successfully
//6. Enter product name in search input and click search button
//7. Verify 'SEARCHED PRODUCTS' is visible
//8. Verify all the products related to search are visible and add to cart
//9. Verify the product quantity

public class TestCaseNine extends BaseTest {

	@Test(groups = { "TestCases" })
	public void SearchProductTestNine() {

		// Step 1,2,and 3 launch browser and go to home page.
		HomePage homePage = new HomePage(getDriver(), wait);
		ProductPage productPage = new ProductPage(getDriver(), wait);
		CartPage cartPage = new CartPage(getDriver(), wait);

		// Step 4 and 5: Click on 'Products' button and verify navigation
		homePage.navigateToProductsPage();

		// Step 6: enter product name and hit search
		String productName = "Blue Top";
		productPage.searchProduct(productName);
		cartPage.getProductQuantity();
	}
}
