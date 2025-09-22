package ecommerceautomation.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import ecommerceautomation.base.BaseTest;
import ecommerceautomation.pages.CartPage;
import ecommerceautomation.pages.HomePage;
import ecommerceautomation.pages.ProductPage;

public class CategoryBrandTest extends BaseTest {
	@Test
	public void checkCategoryAndBrand() {
		// Step 1: click on Women category
		HomePage homePage = new HomePage(getDriver(), wait);
		ProductPage productPage = new ProductPage(getDriver(), wait);
		productPage.clickOnBrandKookieKids();
	}
}
