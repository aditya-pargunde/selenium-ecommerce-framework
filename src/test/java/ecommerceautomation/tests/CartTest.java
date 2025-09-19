package ecommerceautomation.tests;

import ecommerceautomation.base.BaseTest;
import ecommerceautomation.pages.CartPage;
import ecommerceautomation.pages.HomePage;
import ecommerceautomation.pages.ProductPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CartTest extends BaseTest {

	@Test
	public void verifyCartOperations() {
		// this is to remove a product from cart

		// Step 1: Navigate to the Home Page
		HomePage homePage = new HomePage(getDriver(), wait);

		// Step 2: Navigate to Products Page and add a product to the cart
		ProductPage productPage = homePage.navigateToProductsPage();
		String productName = "Blue Top";
		CartPage cartPage = productPage.searchProduct(productName);

		// Step 3: Verify product is in cart and its quantity
		Assert.assertTrue(cartPage.isProductInCart(productName),
				"Expected product to be present in the cart: " + productName);

		// Step 4: Remove the product
		cartPage.removeProduct(productName);

		// Step 5: Verify product was removed
		Assert.assertTrue(cartPage.isProductInCart(productName),
				"Product should no longer be in the cart after removal: " + productName);
	}

	@Test
	public void proceedToCheckoutFlow() {
		// Step 1: Navigate to Products Page and add a product to the cart
		HomePage homePage = new HomePage(getDriver(), wait);
		ProductPage productPage = homePage.navigateToProductsPage();
		String productName = "Blue Top";
		CartPage cartPage = productPage.searchProduct(productName);

		// Step 2: Proceed to checkout from the Cart Page
		cartPage.proceedToCheckout();

		// Step 3: Verify the redirection
		String currentUrl = getDriver().getCurrentUrl();
		Assert.assertTrue(currentUrl.contains("checkout") || currentUrl.contains("login"),
				"After clicking 'Proceed to Checkout', the user should be on the checkout or login page. Current URL: "
						+ currentUrl);
	}

}