package ecommerceautomation.tests;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import ecommerceautomation.base.BaseTest;
import ecommerceautomation.listeners.ExtentTestNGListener;
import ecommerceautomation.pages.CartPage;
import ecommerceautomation.pages.HomePage;
import ecommerceautomation.pages.ProductPage;
import org.testng.Assert;
import org.testng.annotations.Test;

@Listeners(ExtentTestNGListener.class)
public class ProductTest extends BaseTest {

	@Test(groups = {"sanity"})
	public void searchAndAddProductToCart() {
		// Step 1: Start from the home page (handled by BaseTest @BeforeMethod)
		HomePage homePage = new HomePage(getDriver(), wait);

		// Step 2: Navigate to the products page, search for a product, and go to the cart page		
		String productName = "Blue Top";
		CartPage cartPage = homePage.navigateToProductsPage().searchProduct(productName);

		// Step 3: Verify that the product is in the cart
		Assert.assertTrue(cartPage.isProductInCart(productName), "Product should be in the cart");
	}
}