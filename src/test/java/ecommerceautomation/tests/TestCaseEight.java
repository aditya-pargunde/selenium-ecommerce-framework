package ecommerceautomation.tests;

import org.testng.annotations.Test;

import ecommerceautomation.base.BaseTest;
import ecommerceautomation.pages.ContactUsPage;
import ecommerceautomation.pages.HomePage;
import ecommerceautomation.pages.ProductPage;

//Test Case 8: Verify All Products and product detail page
//1. Launch browser
//2. Navigate to url 'http://automationexercise.com'
//3. Verify that home page is visible successfully
//4. Click on 'Products' button
//5. Verify user is navigated to ALL PRODUCTS page successfully
//6. The products list is visible
//7. Click on 'View Product' of first product
//8. User is landed to product detail page
//9. Verify that detail detail is visible: product name, category, price, availability, condition, brand

public class TestCaseEight extends BaseTest{

	@Test(groups = { "TestCases" })
	public void verifyAllProductsTestEight() {

		// Step 1,2,and 3 launch browser and go to home page.
		HomePage homePage = new HomePage(getDriver(), wait);
		ProductPage productPage = new ProductPage(getDriver(), wait);
		
		//Step 4, 5 and 6: click on products to navigate and check if product list is visible
		homePage.navigateToProductsPage();
		productPage.getAllProductList();
		
		//Step 8: Verify that product details are visible:
		productPage.verifyProductDetails();
	}
}
