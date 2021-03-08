package estoresearch;

public class Electronics extends Product {

    private String maker;

    public Electronics() {
        super();
        maker = "";
    }

    public Electronics(String productID, String description, double price, int year, String maker) {
        super(productID, description, price, year);
        this.maker = maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getMaker() {
        return this.maker;
    }

    @Override
    public String toString() {
        String format = "Product ID: " + getProductID() + "\nDescription: " + getDescription() + "\nPrice: " + getPrice() +
                "\nYear: " + getYear() + "\nMaker: " + getMaker();
        return format;
    }

}