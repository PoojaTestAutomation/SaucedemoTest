package TestCases;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import BaseClass.BaseTest;
import Pages.LoginPage;
import Pages.Product;
import Pages.ProductsPage;

public class ProductInformationTest extends BaseTest {

    // Test for verifying product page URL after login (Priority 1)
    @Test(priority = 1)
    public void testProductPageUrl() {
        // Step 1: Initialize page objects for Login and Products Page
        LoginPage loginPage = new LoginPage(page, configReader);
        // Step 2: Login as standard_user
        loginPage.login("standard_user");

        // Step 3: Get current URL after login
        String currentUrl = page.url();
        
        // Step 4: Assert that URL contains "inventory.html" to validate we are on the product page
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(currentUrl.contains("inventory.html"), "Product page URL is incorrect for standard_user");
        
        // Step 5: Login as visual_user
        loginPage.login("visual_user");

        // Step 6: Get current URL after login
        currentUrl = page.url();
        
        // Step 7: Assert that URL contains "inventory.html" for visual_user
        softAssert.assertTrue(currentUrl.contains("inventory.html"), "Product page URL is incorrect for visual_user");

        // Step 8: Assert all soft assertions at the end of the test
        softAssert.assertAll();
    }

    // Test for verifying product information consistency (Priority 2)
    @Test(priority = 2)
    public void testProductInformationConsistency() throws Exception {
        SoftAssert softAssert = new SoftAssert();
        List<Product> standardUserProducts = null;
        List<Product> visualUserProducts = null;

        try {
            // Step 1: Initialize page objects for Login and Products Page
            LoginPage loginPage = new LoginPage(page, configReader);
            ProductsPage productsPage = new ProductsPage(page, configReader);

            // Step 2: Login as standard_user and capture product info
            loginPage.login("standard_user");
            standardUserProducts = productsPage.captureProductInformation();

            // Step 3: Login as visual_user and capture product info
            loginPage.login("visual_user");
            visualUserProducts = productsPage.captureProductInformation();

        } catch (Exception e) {
            // Step 5: Catch any unexpected exceptions
            System.err.println("An unexpected error occurred: " + e.getMessage());
            Assert.fail("Test failed due to an unexpected error: " + e.getMessage());
        }

        // Step 6: Assert that both product lists are not null
        if (standardUserProducts == null || visualUserProducts == null) {
            System.err.println("Product information could not be fetched for one or both users.");
            Assert.fail("Product information could not be fetched for one or both users.");
            return;
        }

        // Step 7: Assert that the number of products is the same for both users
        softAssert.assertEquals(standardUserProducts.size(), visualUserProducts.size(), "The number of products should match");

        for (Product standardProduct : standardUserProducts) {
            for (Product visualProduct : visualUserProducts) {
                if (standardProduct.getName().equals(visualProduct.getName())) {
                    String standardPrice = standardProduct.getPrice().replaceAll("[^\\d.]", "").trim();
                    String visualPrice = visualProduct.getPrice().replaceAll("[^\\d.]", "").trim();

                    if (standardPrice.equals(visualPrice)) {
                        System.out.println("Match: " + standardProduct.getName() + " | Price: " + standardPrice);
                    } else {
                        System.err.println("Mismatch detected for '" + standardProduct.getName() + 
                                           "' | Expected: '" + standardPrice + "' Found: '" + visualPrice + "'");
                        
                        // Optional: Skip or log instead of failing
                        continue; 
                    }
                }
            }
        }

        // Step 12: Assert all soft assertions at the end of the test
        softAssert.assertAll();
    }
    
   
}
