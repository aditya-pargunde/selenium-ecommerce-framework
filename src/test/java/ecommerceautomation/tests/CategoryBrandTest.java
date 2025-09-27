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
		// Step 1: click on Women category
		HomePage homePage = new HomePage(getDriver(), wait);
		ProductPage productPage = new ProductPage(getDriver(), wait);
		productPage.clickOnWomenCategory();
		productPage.clickOnWomenDressSubCategory();
		productPage.clickOnWomenSareeSubCategory();
		productPage.clickOnWomenTopsSubCategory();
		productPage.clickOnMenCategory();
		productPage.clickOnMenJeansSubCategory();
		productPage.clickOnMenTShirtsSubCategory();
		productPage.clickOnKidsCategory();
		productPage.clickOnKidsDressSubCategory();
		productPage.clickOnKidsTopsAndShirtsSubCategory();
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
