package Pages;

import java.util.List;

// Model class to represent a Product with name and price
public class Product {

    private String name;
    private String price;

    // Constructor to initialize Product object with name and price
    public Product(String string, String string2) {
        this.name = string;
        this.price = string2;
    }

    // Getters and setters for name and price
    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
}
