package Pages;

import java.util.ArrayList;
import java.util.List;

import com.microsoft.playwright.Page;

import Utils.ConfigReader;

public class ProductsPage {

    private Page page;
    private ConfigReader configReader;

    // Constructor to initialize ProductsPage class
    public ProductsPage(Page page, ConfigReader configReader) {
        this.page = page;
        this.configReader = configReader;
    }

    // Method to capture product information (name and price) from the product page
    public List<Product> captureProductInformation() {
        List<Product> products = new ArrayList<>();
        
        // Get all product names and prices from the page
        List<String> productNames = page.locator(".inventory_item_name").allTextContents();
        List<String> productPrices = page.locator(".inventory_item_price").allTextContents();

        // Populate products list with name and price data
        for (int i = 0; i < productNames.size(); i++) {
            products.add(new Product(productNames.get(i), productPrices.get(i)));
        }
        return products; // Return list of products
    }

    // Navigate to the base URL (useful if a specific navigation is needed)
    public void navigateToBaseUrl() {
        page.navigate(configReader.getBaseUrl());
    }
}
