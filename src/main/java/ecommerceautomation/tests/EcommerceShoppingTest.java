package ecommerceautomation.tests;

import ecommerceautomation.base.BaseTest;
import ecommerceautomation.pages.LoginPage;
import ecommerceautomation.pages.ProductPage;
import ecommerceautomation.pages.CartPage;
import ecommerceautomation.pages.CheckoutPage;
import ecommerceautomation.utils.ExcelUtils;
import ecommerceautomation.utils.JsonUtils;
import org.testng.annotations.Test;

import java.util.List;

public class EcommerceShoppingTest extends BaseTest {

    @Test(groups = { "regression" })
    public void fullEndToEndFlow() {
        // Initialize Page Objects
        LoginPage login = new LoginPage(getDriver());
        ProductPage product = new ProductPage(getDriver());
        CartPage cart = new CartPage(getDriver());
        CheckoutPage checkout = new CheckoutPage(getDriver());

        try {
            // 1️⃣ Login with Excel Data
            ExcelUtils excel = new ExcelUtils("src/test/resources/users.xlsx", "ValidLoginUsers");
            String[] validUser = excel.getFirstRow(); // first row = valid credentials
            login.login(validUser[0], validUser[1]);

            // 2️⃣ Add multiple products (from JSON)
            JsonUtils json = new JsonUtils("src/test/resources/products.json");
            List<String> productsToAdd = json.getStringList("products");

            for (String prodName : productsToAdd) {
                product.searchProduct(prodName);
             //   product.addFirstProductToCart();
            }

            // 3️⃣ Verify product quantities in cart
//            List<Integer> quantities = cart.getAllProductQuantities();
//            for (int qty : quantities) {
//                System.out.println("Product quantity in cart: " + qty);
//            }

            // 4️⃣ Remove a specific product from cart
           // cart.removeProduct("Jeans");

            // 5️⃣ Checkout / Place Order
            checkout.fillShipmentDetails("John Doe", "123 Main Street", "New York", "10001", "USA");
            checkout.placeOrder();

            // 6️⃣ Download Invoice
            checkout.downloadInvoice();

            // 7️⃣ Logout
            login.logout();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
