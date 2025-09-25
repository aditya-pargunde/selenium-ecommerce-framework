package ecommerceautomation.pages;

import ecommerceautomation.utils.WaitUtils;
import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class ProductPage {

	private WebDriver driver;
	private WaitUtils wait;
	private static Properties locators;

	static {
		locators = new Properties();
		try (FileInputStream fis = new FileInputStream("src/main/resources/locators.properties")) {
			locators.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to load locators.properties file.");
		}
	}

	public ProductPage(WebDriver driver, WaitUtils wait) {
		this.driver = driver;
		this.wait = wait;
	}

	// ------------------- By Locators -------------------
	private By productHeader = By.xpath(locators.getProperty("product.productsLink"));
	private By searchBox = By.xpath(locators.getProperty("product.searchBox"));
	private By searchButton = By.xpath(locators.getProperty("product.searchButton"));
	private By productListLocator = By.xpath(locators.getProperty("product.productListLocator"));
	private By modalContent = By.xpath(locators.getProperty("product.modalContent"));
	private By viewCartLink = By.xpath(locators.getProperty("product.viewCartLink"));
	private By subscriptionText = By.xpath(locators.getProperty("home.subscriptionText"));
	private By viewProductButton = By.xpath(locators.getProperty("product.viewProductButton"));
	private By continueShopping = By.xpath(locators.getProperty("product.continueShopping"));

	private By womenCategory = By.xpath(locators.getProperty("product.womenCategory"));
	private By womenDress = By.xpath(locators.getProperty("women.womenDress"));
	private By womenTops = By.xpath(locators.getProperty("women.womenTops"));
	private By womenSaree = By.xpath(locators.getProperty("women.womenSaree"));

	private By menCategory = By.xpath(locators.getProperty("product.menCategory"));
	private By menTShirts = By.xpath(locators.getProperty("men.menTShirts"));
	private By menJeans = By.xpath(locators.getProperty("men.menJeans"));

	private By kidsCategory = By.xpath(locators.getProperty("product.kidsCategory"));
	private By kidsDress = By.xpath(locators.getProperty("kids.kidsDress"));
	private By kidsTopsAndTShirts = By.xpath(locators.getProperty("kids.kidsTopsAndTShirts"));

	private By brandPolo = By.xpath(locators.getProperty("product.brandPolo"));
	private By brandHandM = By.xpath(locators.getProperty("product.brandHandM"));
	private By brandMadame = By.xpath(locators.getProperty("product.brandMadame"));
	private By brandMastAndHarbour = By.xpath(locators.getProperty("product.brandMastAndHarbour"));
	private By brandBabyhug = By.xpath(locators.getProperty("product.brandBabyhug"));
	private By brandAllenSolleyJunior = By.xpath(locators.getProperty("product.brandAllenSolleyJunior"));
	private By brandKookieKids = By.xpath(locators.getProperty("product.brandKookieKids"));
	private By brandBiba = By.xpath(locators.getProperty("product.brandBiba"));
	private By brandCategoryHeaderText = By.xpath(locators.getProperty("product.brandCategoryHeaderText"));

	// ------------------- Public Methods -------------------
	public CartPage searchProduct(String productName) {
		wait.waitForElementToBeClickable(productHeader).click();
		performSearch(productName);
		WebElement productElement = findProductInList(productName);
		if (productElement != null) {
			addProductToCart(productElement);
		} else {
			throw new RuntimeException("Product '" + productName + "' not found on the page.");
		}
		return handleAddToCartModal();
	}

	// ------------------- Private Helper Methods -------------------
	private void performSearch(String productName) {
		wait.waitForElementToBeVisible(searchBox).sendKeys(productName);
		wait.waitForElementToBeClickable(searchButton).click();
	}

	private WebElement findProductInList(String productName) {
		List<WebElement> products = wait.waitForAllElementsToBeVisible(productListLocator);
		for (WebElement product : products) {
			WebElement searchedProductName = product.findElement(By.tagName("p"));
			if (searchedProductName.getText().equalsIgnoreCase(productName)) {
				return product;
			}
		}
		return null;
	}

	private void addProductToCart(WebElement productElement) {
		Actions actions = new Actions(driver);
		// scroll to view product button due to advertisement on page
		wait.scrollIntoView(viewProductButton);

		// Hover over the product container
		actions.moveToElement(productElement).perform();

		// Now, wait and find the 'Add to cart' button after hovering
		productElement.findElement(viewProductButton);
		WebElement addToCartButton = productElement.findElement(By.xpath(".//a[contains(text(),'Add to cart')]"));
		addToCartButton.click();
	}

	private CartPage handleAddToCartModal() {
		wait.waitForElementToBeVisible(modalContent);
		wait.waitForElementToBeClickable(viewCartLink).click();
		return new CartPage(driver, wait);
	}

	private HomePage handleContinueShoppingModal() {
		wait.waitForAllElementsToBeVisible(continueShopping);
		wait.waitForElementToBeClickable(continueShopping);
		return new HomePage(driver, wait);
	}

	public String clickOnWomenCategory() {
		wait.waitForAllElementsToBeVisible(womenCategory);
		wait.waitForElementToBeClickable(womenCategory).click();
		String categoryName = driver.findElement(By.xpath(locators.getProperty("product.womenCategory"))).getText();
		return categoryName;
	}

	public void clickOnWomenDressSubCategory() {
		clickOnWomenCategory();
		wait.waitForAllElementsToBeVisible(womenDress);
		String categoryName = driver.findElement(By.xpath(locators.getProperty("product.womenCategory"))).getText();
		String SubCategoryName = driver.findElement(By.xpath(locators.getProperty("women.womenDress"))).getText();
		wait.waitForElementToBeClickable(womenDress).click();
		categoryHeader(categoryName, SubCategoryName);
	}

	public void clickOnWomenTopsSubCategory() {
		clickOnWomenCategory();
		wait.waitForAllElementsToBeVisible(womenTops);
		String categoryName = driver.findElement(By.xpath(locators.getProperty("product.womenCategory"))).getText();
		String SubCategoryName = driver.findElement(By.xpath(locators.getProperty("women.womenTops"))).getText();
		wait.waitForElementToBeClickable(womenTops).click();
		categoryHeader(categoryName, SubCategoryName);
	}

	public void clickOnWomenSareeSubCategory() {
		clickOnWomenCategory();
		wait.waitForAllElementsToBeVisible(womenSaree);
		String categoryName = driver.findElement(By.xpath(locators.getProperty("product.womenCategory"))).getText();
		String SubCategoryName = driver.findElement(By.xpath(locators.getProperty("women.womenSaree"))).getText();
		wait.waitForElementToBeClickable(womenSaree).click();
		categoryHeader(categoryName, SubCategoryName);
	}

	public void clickOnMenCategory() {
		wait.waitForAllElementsToBeVisible(menCategory);
		wait.waitForElementToBeClickable(menCategory).click();
	}

	public void clickOnMenTShirtsSubCategory() {
		clickOnMenCategory();
		wait.waitForAllElementsToBeVisible(menTShirts);
		String categoryName = driver.findElement(By.xpath(locators.getProperty("product.menCategory"))).getText();
		String SubCategoryName = driver.findElement(By.xpath(locators.getProperty("men.menTShirts"))).getText();
		wait.waitForElementToBeClickable(menTShirts).click();
		categoryHeader(categoryName, SubCategoryName);
	}

	public void clickOnMenJeansSubCategory() {
		clickOnMenCategory();
		wait.waitForAllElementsToBeVisible(menJeans);
		String categoryName = driver.findElement(By.xpath(locators.getProperty("product.menCategory"))).getText();
		String SubCategoryName = driver.findElement(By.xpath(locators.getProperty("men.menJeans"))).getText();
		wait.waitForElementToBeClickable(menJeans).click();
		categoryHeader(categoryName, SubCategoryName);
	}

	public void clickOnKidsCategory() {
		wait.waitForAllElementsToBeVisible(kidsCategory);
		wait.waitForElementToBeClickable(kidsCategory).click();
	}

	public void clickOnKidsDressSubCategory() {
		clickOnKidsCategory();
		wait.waitForAllElementsToBeVisible(kidsDress);
		String categoryName = driver.findElement(By.xpath(locators.getProperty("product.kidsCategory"))).getText();
		String SubCategoryName = driver.findElement(By.xpath(locators.getProperty("kids.kidsDress"))).getText();
		wait.waitForElementToBeClickable(kidsDress).click();
		categoryHeader(categoryName, SubCategoryName);
	}

	public void clickOnKidsTopsAndShirtsSubCategory() {
		clickOnKidsCategory();
		wait.waitForAllElementsToBeVisible(kidsTopsAndTShirts);
		String categoryName = driver.findElement(By.xpath(locators.getProperty("product.kidsCategory"))).getText();
		String SubCategoryName = driver.findElement(By.xpath(locators.getProperty("kids.kidsTopsAndTShirts")))
				.getText();
		wait.waitForElementToBeClickable(kidsTopsAndTShirts).click();
		categoryHeader(categoryName, SubCategoryName);
	}

	public void categoryHeader(String categoryName, String subCategoryName) {
		String categoryHeaderText = driver.findElement(By.xpath("//h2[@class='title text-center']")).getText();
		Assert.assertEquals(categoryHeaderText, "" + categoryName + " - " + subCategoryName + " PRODUCTS");
	}

	public void brandHeader(String brandName) {
		String brandHeaderText = driver.findElement(By.xpath(locators.getProperty("product.brandCategoryHeaderText")))
				.getText();
		//Assert.assertEquals(brandHeaderText, "Brand -"+ brandName +" Products");
	}

	public String clickOnBrandPolo() {
		wait.waitForAllElementsToBeVisible(brandPolo);
		String brandName = driver.findElement(By.xpath(locators.getProperty("product.brandPolo"))).getText();
		wait.waitForElementToBeClickable(brandPolo).click();
		brandHeader(brandName);
		return brandName;
	}

	public String clickOnBrandHAndM() {
		wait.waitForAllElementsToBeVisible(brandHandM);
		String brandName = driver.findElement(By.xpath(locators.getProperty("product.brandHandM"))).getText();
		wait.waitForElementToBeClickable(brandHandM).click();
		brandHeader(brandName);
		return brandName;
	}

	public String clickOnBrandMadame() {
		wait.waitForAllElementsToBeVisible(brandMadame);
		String brandName = driver.findElement(By.xpath(locators.getProperty("product.brandMadame"))).getText();
		wait.waitForElementToBeClickable(brandMadame).click();
		brandHeader(brandName);
		return brandName;
	}

	public String clickOnBrandMastAndHarbour() {
		wait.waitForAllElementsToBeVisible(brandMastAndHarbour);
		String brandName = driver.findElement(By.xpath(locators.getProperty("product.brandMastAndHarbour"))).getText();
		wait.waitForElementToBeClickable(brandMastAndHarbour).click();
		brandHeader(brandName);
		return brandName;
	}

	public String clickOnBrandBabyhug() {
		wait.waitForAllElementsToBeVisible(brandBabyhug);
		String brandName = driver.findElement(By.xpath(locators.getProperty("product.brandBabyhug"))).getText();
		wait.waitForElementToBeClickable(brandBabyhug).click();
		brandHeader(brandName);
		return brandName;
	}

	public String clickOnBrandAllenSolleyJunior() {
		wait.waitForAllElementsToBeVisible(brandAllenSolleyJunior);
		String brandName = driver.findElement(By.xpath(locators.getProperty("product.brandAllenSolleyJunior")))
				.getText();
		wait.waitForElementToBeClickable(brandAllenSolleyJunior).click();
		brandHeader(brandName);
		return brandName;
	}

	public String clickOnBrandKookieKids() {
		wait.waitForAllElementsToBeVisible(brandKookieKids);
		String brandName = driver.findElement(By.xpath(locators.getProperty("product.brandKookieKids"))).getText();
		wait.waitForElementToBeClickable(brandKookieKids).click();
		brandHeader(brandName);
		return brandName;
	}

	public String clickOnBrandBiba() {
		wait.waitForAllElementsToBeVisible(brandBiba);
		String brandName = driver.findElement(By.xpath(locators.getProperty("product.brandBiba"))).getText();
		wait.waitForElementToBeClickable(brandBiba).click();
		brandHeader(brandName);
		return brandName;
	}
}