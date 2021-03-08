package estoresearch;

public class Product {

    private String productID;
    private String description;
    private double price;
    private int year;

    public Product(String productID, String description, double price, int year) {
        this.productID = productID;
        this.description = description;
        this.price = price;
        this.year = year;
    }

    public Product() {
        productID = "";
        description = "";
        price = 0;
        year = 0;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductID() {
        return this.productID;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return this.price;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getYear() {
        return this.year;
    }
}
