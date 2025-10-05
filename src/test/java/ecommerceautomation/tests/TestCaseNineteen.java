package ecommerceautomation.tests;

import org.testng.annotations.Test;

import ecommerceautomation.base.BaseTest;
import ecommerceautomation.pages.ProductPage;

//Test Case 19: View & Cart Brand Products
//1. Launch browser
//2. Navigate to url 'http://automationexercise.com'
//3. Click on 'Products' button
//4. Verify that Brands are visible on left side bar
//5. Click on any brand name
//6. Verify that user is navigated to brand page and brand products are displayed
//7. On left side bar, click on any other brand link
//8. Verify that user is navigated to that brand page and can see products

public class TestCaseNineteen extends BaseTest{
	@Test(groups = { "TestCases" })
	public void BrandAndCategoryTestNineteen(){
		ProductPage productPage = new ProductPage(getDriver(), wait);
		productPage.clickOnWomenCategory();
		productPage.clickOnWomenDressSubCategory();
		productPage.clickOnWomenTopsSubCategory();
		productPage.clickOnWomenSareeSubCategory();
		productPage.clickOnMenCategory();
		productPage.clickOnMenTShirtsSubCategory();
		productPage.clickOnMenJeansSubCategory();
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
