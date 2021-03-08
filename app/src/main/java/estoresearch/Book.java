package estoresearch;

public class Book extends Product {

    private String authors;
    private String publisher;

    public Book() {
        super();
        authors = "";
        publisher = "";
    }

    public Book(String productID, String description, double price, int year, String authors, String publisher) {
        super(productID, description, price, year);
        this.authors = authors;
        this.publisher = publisher;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getAuthors() {
        return this.authors;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublisher() {
        return this.publisher;
    }

    @Override
    public String toString() {
        String format = "Product ID: " + getProductID() + "\nDescription: " + getDescription() + "\nPrice: " + getPrice() +
                "\nYear: " + getYear() + "\nAuthors: " + this.authors + "\nPublisher: " + this.publisher;
        return format;
    }

}