/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package estoresearch;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import javax.swing.JFrame;

public class App {

    static Scanner userInput = new Scanner(System.in); //scanner to use across the various methods in this class

    public static void main(String[] args) {
        boolean quitLoop = false;
        String command;
        EStoreSearch eStore = new EStoreSearch();
        String fileName = args[0];

        Scanner inputStream = null;
        PrintWriter fileWriter = null;

        JFrame firstWindow = new JFrame();
        firstWindow.setSize(300, 200);
        firstWindow.setVisible(true);
        firstWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            inputStream = new Scanner(new FileInputStream(fileName));
        } catch (Exception e) {
            System.out.println("File not found. Program is exiting...");
            System.exit(0);
        }

        parseFile(inputStream, eStore);

        while (quitLoop == false) {
            displayMenu();
            command = userInput.nextLine().toLowerCase();

            if (command.equals("add") || command.equals("a")) {
                //add object
                addProduct(eStore);
            } else if (command.equals("search") || command.equals("s")) {
                //search object
                searchProduct(eStore);
            } else if (command.equals("quit") || command.equals("q")) {
                //quit loop
                quitLoop = true;
            } else {
                System.out.println("Command not recognized. Please try again.");
            }            
        }

        try {
            fileWriter = new PrintWriter(fileName, "UTF-8");
        } catch (Exception e) {
            System.out.println("Failed to open file. Program is exiting...");
        }

        updateFile(fileWriter, eStore);

        userInput.close();
        inputStream.close();
        fileWriter.close();
    }

    public static void displayMenu() {
        System.out.println();
        System.out.println("Commands:");
        System.out.println("ADD: Add a book or electronic to the eStore.");
        System.out.println("SEARCH: Search for a book or electronic in the eStore.");
        System.out.println("QUIT: Exit the program.");
        System.out.println("Input your command:");
        System.out.println();
    }

    public static void parseFile(Scanner inputStream, EStoreSearch eStore) {
        String productID = "";
        String description = "";
        String wordPrice = "";
        double price = 0;
        String wordYear = "";
        int year = 0;
        String authors = "";
        String publisher = "";
        String maker = "";
        Book newBook = new Book();
        Electronics newElectronics = new Electronics();

        while(inputStream.hasNextLine()) {
            String line = inputStream.nextLine();
            if (line.substring(line.indexOf('"') + 1, line.length() - 1).equals("book") && !(line.isEmpty())) { //book
                line = inputStream.nextLine();
                productID = line.substring(line.indexOf('"') + 1, line.length() - 1);

                line = inputStream.nextLine();
                description = line.substring(line.indexOf('"') + 1, line.length() - 1);

                line = inputStream.nextLine();
                wordPrice = line.substring(line.indexOf('"') + 1, line.length() - 1);
                price = Double.parseDouble(wordPrice);

                line = inputStream.nextLine();
                wordYear = line.substring(line.indexOf('"') + 1, line.length() - 1);
                year = Integer.parseInt(wordYear);

                line = inputStream.nextLine();
                authors = line.substring(line.indexOf('"') + 1, line.length() - 1);

                line = inputStream.nextLine();
                publisher = line.substring(line.indexOf('"') + 1, line.length() - 1);

                newBook = new Book(productID, description, price, year, authors, publisher);
                eStore.addProduct(newBook);

            } else if (!(line.isEmpty())){ //electronics
                line = inputStream.nextLine();
                productID = line.substring(line.indexOf('"') + 1, line.length() - 1);

                line = inputStream.nextLine();
                description = line.substring(line.indexOf('"') + 1, line.length() - 1);

                line = inputStream.nextLine();
                wordPrice = line.substring(line.indexOf('"') + 1, line.length() - 1);
                price = Double.parseDouble(wordPrice);

                line = inputStream.nextLine();
                wordYear = line.substring(line.indexOf('"') + 1, line.length() - 1);
                year = Integer.parseInt(wordYear);

                line = inputStream.nextLine();
                maker = line.substring(line.indexOf('"') + 1, line.length() - 1);

                newElectronics = new Electronics(productID, description, price, year, maker);
                eStore.addProduct(newElectronics);
            }
        }
    }

    public static void updateFile(PrintWriter fileWriter, EStoreSearch eStore) {
        ArrayList<Product> productList = new ArrayList<Product>(eStore.getProductList());
        Book tempBook = new Book();
        Electronics tempElectronics = new Electronics();

        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getClass().equals(tempBook.getClass())) { //if product is a book
                tempBook = (Book)productList.get(i);
                fileWriter.println("type = \"book\"");
                fileWriter.println("productID = \"" + tempBook.getProductID() + "\"");
                fileWriter.println("description = \"" + tempBook.getDescription() + "\"");
                fileWriter.println("price = \"" + tempBook.getPrice() + "\"");
                fileWriter.println("year = \"" + tempBook.getYear() + "\"");
                fileWriter.println("authors = \"" + tempBook.getAuthors() + "\"");
                fileWriter.println("publisher = \"" + tempBook.getPublisher() + "\"");
            } else { //if product is an electronic
                tempElectronics = (Electronics)productList.get(i);
                fileWriter.println("type = \"electronics\"");
                fileWriter.println("productID = \"" + tempElectronics.getProductID() + "\"");
                fileWriter.println("description = \"" + tempElectronics.getDescription() + "\"");
                fileWriter.println("price = \"" + tempElectronics.getPrice() + "\"");
                fileWriter.println("year = \"" + tempElectronics.getYear() + "\"");
                fileWriter.println("maker = \"" + tempElectronics.getMaker() + "\"");
            }
        }

    }

    public static void addProduct(EStoreSearch eStore) {
        boolean pass = false;
        while (pass == false) {
            System.out.println("Are you adding a book or an electronics product to the store?");
            String itemType = userInput.nextLine().toLowerCase();
            if (itemType.equals("book")) {
                Book newBook = createBook(eStore);
                eStore.addProduct(newBook);
                pass = true;
            } else if (itemType.equals("electronics")) {
                Electronics newElectronics = createElectronics(eStore);
                eStore.addProduct(newElectronics);
                pass = true;
            } else {
                System.out.println("Input not recognized. Please try again.");
            }
        }
    }

    public static void searchProduct(EStoreSearch eStore) {
        boolean pass = false;
        String productID = "";
        String timePeriod = "";
        Book tempBook = new Book();
        Electronics tempElectronics = new Electronics();

        System.out.println("Input the ID of the product you are searching for (leave blank if N/A): ");
        productID = userInput.nextLine();
        System.out.println("Input your search phrase (leave blank if N/A): ");
        String searchWords[] = userInput.nextLine().toLowerCase().split("\\W+");
        System.out.println("Input the time period of the product (leave blank if N/A): ");
        timePeriod = userInput.nextLine();

        //LOOP ABOVE CONSTRUCT LATER (error handling etc)

        ArrayList<Product> searchedProducts = eStore.searchProducts(productID, searchWords, timePeriod);

        if (searchedProducts.size() != 0) {
            for (int i = 0; i < searchedProducts.size(); i++) {
                if (searchedProducts.get(i).getClass().equals(tempBook.getClass())) { //if object is a book
                    tempBook = (Book)searchedProducts.get(i);
                    System.out.println("Product Type: Book");
                    System.out.println(tempBook.toString());
                    System.out.println();
                } else {
                    tempElectronics = (Electronics)searchedProducts.get(i);
                    System.out.println("Product Type: Electronics");
                    System.out.println(tempElectronics.toString());
                    System.out.println();
                }
            }
        } else {
            System.out.println("No products found.");
        }
    }

    public static Book createBook(EStoreSearch eStore) {
        String productID = "";
        String description = "";
        String wordPrice = "";
        double price = -1;
        String wordYear = "";
        int year = 0;
        String authors = "";
        String publisher = "";

        while(productID.equals("")) {
            System.out.print("Input the books' product ID: ");
            productID = userInput.nextLine();

            for (int i = 0; i < productID.length(); i++) {
                if (!(Character.isDigit(productID.charAt(i)))) {
                    System.out.println("The product ID must be a 6 digit long number. Please try again.");
                    productID = "";
                    break;
                }
            }

            if (productID.length() != 6) {
                System.out.println("The product ID must be a 6 digit long number. Please try again.");
                productID = "";
            }

            if (!(eStore.checkValidProductID(productID))) {
                System.out.println("The product ID inputted already exists. Please try again.");
                productID = "";
            }

            System.out.println();
        }

        while(description.equals("")) {
            System.out.print("Input the books' description: ");
            description = userInput.nextLine();
            if (description.equals("")) {
                System.out.println("The book must have a description. Please try again.");
            }
            System.out.println();
        }

        while (price < 0) {
            System.out.print("Input the books' price (leave blank if N/A): ");
            wordPrice = userInput.nextLine();
            
            if (wordPrice.equals("")) {
                price = 0;
            } else {
                price = Double.parseDouble(wordPrice);
            }

            if (price < 0) {
                System.out.println("The price cannot be negative. Please try again.");
            }
            System.out.println();
        }

        while (year < 1000 || year > 9999) {
            System.out.print("Input the books' year: ");
            wordYear = userInput.nextLine();
            year = Integer.parseInt(wordYear);
            if (year < 1000 || year > 9999) {
                System.out.println("The year must be a value between 1000 and 9999. Please try again.");
            }
            System.out.println();
        }

        System.out.print("Input the books' authors (leave blank if no value): ");
        authors = userInput.nextLine();
        System.out.println();

        System.out.print("Input the books' publisher (leave blank if no value): ");
        publisher = userInput.nextLine();
        System.out.println();

        return new Book(productID, description, price, year, authors, publisher);
    }

    public static Electronics createElectronics(EStoreSearch eStore) {
        String productID = "";
        String description = "";
        String wordPrice = "";
        double price = -1;
        String wordYear = "";
        int year = 0;
        String maker = "";

        while(productID.equals("")) {
            System.out.print("Input the electronics' product ID: ");
            productID = userInput.nextLine();

            for (int i = 0; i < productID.length(); i++) {
                if (!(Character.isDigit(productID.charAt(i)))) {
                    System.out.println("The product ID must be a 6 digit long number. Please try again.");
                    productID = "";
                    break;
                }
            }

            if (productID.length() != 6) {
                System.out.println("The product ID must be a 6 digit long number. Please try again.");
                productID = "";
            }

            if (eStore.checkValidProductID(productID) == false) {
                System.out.println("The product ID inputted already exists. Please try again.");
                productID = "";
            }

            System.out.println();
        }

        while(description.equals("")) {
            System.out.print("Input the books' description: ");
            description = userInput.nextLine();
            if (description.equals("")) {
                System.out.println("The book must have a description. Please try again.");
            }
            System.out.println();
        }

        while (price < 0) {
            System.out.print("Input the books' price (leave blank if N/A): ");
            wordPrice = userInput.nextLine();
            
            if (wordPrice.equals("")) {
                price = 0;
            } else {
                price = Double.parseDouble(wordPrice);
            }

            if (price < 0) {
                System.out.println("The price cannot be negative. Please try again.");
            }
            System.out.println();
        }

        while (year < 1000 || year > 9999) {
            System.out.print("Input the books' year: ");
            wordYear = userInput.nextLine();
            year = Integer.parseInt(wordYear);
            if (year < 1000 || year > 9999) {
                System.out.println("The year must be a value between 1000 and 9999. Please try again.");
            }
            System.out.println();
        }

        System.out.println("Input the electronics' maker (leave blank if no value): ");
        maker = userInput.nextLine();
        System.out.println();

        return new Electronics(productID, description, price, year, maker);
    }
}
