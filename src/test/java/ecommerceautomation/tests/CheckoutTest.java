package ecommerceautomation.tests;

import ecommerceautomation.base.BaseTest;
import ecommerceautomation.pages.HomePage;
import ecommerceautomation.pages.LoginPage;
import ecommerceautomation.pages.ProductPage;
import ecommerceautomation.pages.CartPage;
import ecommerceautomation.pages.CheckoutPage;
import ecommerceautomation.pages.PaymentPage;
import ecommerceautomation.pages.OrderConfirmationPage;
import ecommerceautomation.utils.ExcelUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class CheckoutTest extends BaseTest {

    @DataProvider(name = "checkoutData")
    public Object[][] getCheckoutData() throws Exception {
        // Updated to get the file path from a config file for better maintainability
        ExcelUtils excel = new ExcelUtils(config.getProperty("user.data.path"), "RegisterUsers");
        List<String[]> allRows = excel.getAllRows();

        Object[][] data = new Object[allRows.size()][];
        for (int i = 0; i < allRows.size(); i++) {
            data[i] = allRows.get(i); // each row contains: name, address, city, zipcode, country
        }
        return data;
    }

    @Test(dataProvider = "checkoutData")
    public void placeOrderTest(String name, String email, String password, String title, String day, String month, String year, String firstName, String lastName, String company, String address, String country, String state, String city, String zipcode,  String mobilenumber) {
        // Step 1: Start from the home page and add a product to the cart
        HomePage homePage = new HomePage(getDriver(), wait);
        ProductPage productPage = homePage.navigateToProductsPage();
        CartPage cartPage = productPage.searchProduct("Blue Top");
        LoginPage loginPage = new LoginPage(getDriver(), wait);
        
        // Step 2: Proceed to checkout from the Cart Page
        CheckoutPage checkoutPage = cartPage.proceedToCheckout();
        loginPage.enterEmailForLogin(email);
        loginPage.enterPasswordForLogin(password);
        loginPage.clickOnLoginButton();
        cartPage.cartPageLink();
        cartPage.proceedToCheckout();
        
        
        // Step 3: Compare delivery and billing addresses
        List<String> deliveryDetails = checkoutPage.getDeliveryAddressDetails();
        List<String> billingDetails = checkoutPage.getBillingAddressDetails();
        
        Assert.assertEquals(deliveryDetails, billingDetails, "Delivery and billing addresses should match.");

        // Step 4: Place the order and navigate to the payment page
        PaymentPage paymentPage = checkoutPage.placeOrder();
//
//        // Optional: You could add a test to fill out payment details here
//        // paymentPage.enterCardDetails(...)
//        // OrderConfirmationPage confirmationPage = paymentPage.confirmOrder();
//        
//        // Step 5: Assert that we are on the payment page
//        String currentUrl = getDriver().getCurrentUrl();
//        Assert.assertTrue(currentUrl.contains("payment"), "Expected to be on the payment page after placing the order.");
    }
}