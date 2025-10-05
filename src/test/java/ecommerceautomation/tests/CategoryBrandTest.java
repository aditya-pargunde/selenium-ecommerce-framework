package ecommerceautomation.tests;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.annotations.Test;
import ecommerceautomation.base.BaseTest;
import ecommerceautomation.listeners.ExtentTestNGListener;
import ecommerceautomation.pages.CartPage;
import ecommerceautomation.pages.HomePage;
import ecommerceautomation.pages.ProductPage;

@Listeners(ExtentTestNGListener.class)
public class CategoryBrandTest extends BaseTest {
	@Test(groups= {"regression"})
	public void checkCategoryAndBrand() {
		HomePage homePage = new HomePage(getDriver(), wait);
		ProductPage productPage = new ProductPage(getDriver(), wait);
		// Step 1: click on Women category and sub category
		productPage.clickOnWomenCategory();
		productPage.clickOnWomenDressSubCategory();
		productPage.clickOnWomenSareeSubCategory();
		productPage.clickOnWomenTopsSubCategory();
		
		// Step 2: click on Men category and sub category
		productPage.clickOnMenCategory();
		productPage.clickOnMenJeansSubCategory();
		productPage.clickOnMenTShirtsSubCategory();
		
		// Step 3: click on Kids category and sub category
		productPage.clickOnKidsCategory();
		productPage.clickOnKidsDressSubCategory();
		productPage.clickOnKidsTopsAndShirtsSubCategory();
		
		// Step 4: click on each brand category 
		productPage.clickOnBrandPolo();
		productPage.clickOnBrandHAndM();
		productPage.clickOnBrandMadame();
		productPage.clickOnBrandMastAndHarbour();
		productPage.clickOnBrandBabyhug();
		productPage.clickOnBrandAllenSolleyJunior();
		productPage.clickOnBrandKookieKids();
		productPage.clickOnBrandBiba();
	}
}
