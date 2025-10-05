package ecommerceautomation.tests;

import org.testng.annotations.Test;

import ecommerceautomation.base.BaseTest;
import ecommerceautomation.pages.CartPage;
import ecommerceautomation.pages.HomePage;
import ecommerceautomation.pages.ProductPage;

//Test Case 17: Remove Products From Cart
//1. Launch browser
//2. Navigate to url 'http://automationexercise.com'
//3. Verify that home page is visible successfully
//4. Add products to cart
//5. Click 'Cart' button
//6. Verify that cart page is displayed
//7. Click 'X' button corresponding to particular product
//8. Verify that product is removed from the cart

public class TestCaseSeventeen extends BaseTest{
	@Test(groups = { "TestCases" })
	public void RemoveProductFromCartTestSeventeen() {

		// Step 1,2,and 3 launch browser and go to home page.
		HomePage homePage = new HomePage(getDriver(), wait);
		ProductPage productPage = new ProductPage(getDriver(), wait);
		CartPage cartPage = new CartPage(getDriver(), wait);

		// Step 4, 5 and 6: Click on 'Products' button and verify navigation
		homePage.navigateToProductsPage();

		// Step 7: remove product and verify removal 
		String productName = "Sleeveless Dress";
		productPage.searchProduct(productName);
		cartPage.getProductQuantity();
		cartPage.removeProduct(productName);
		homePage.navigateToProductsPage();
		cartPage.cartPageLink();
		cartPage.verifyProductRemoval();
	}
}
