/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package estoresearch;

import org.junit.Test;
import static org.junit.Assert.*;

public class ElectronicsTest {
    @Test
    public void testElectronicsConstructor() {
        /* declaring variables */

        String productID = "067192";
        String description = "Really Cool Computer";
        double price = 2020.10;
        int year = 2000;
        String maker = "Apple";

        /* testing empty constructor */

        Electronics testElectronics = new Electronics();
        assertNotNull("testElectronics should be a created object with values from empty constructor", testElectronics);

        /* testing constructor */

        Electronics testElectronics2 = new Electronics(productID, description, price, year, maker);
        assertNotNull("testElectronics should be a created object with values from empty constructor", testElectronics);
    }

    @Test
    public void testElectronicsGetValues() {
        Electronics testElectronics = new Electronics("067192", "Really Cool Computer", 2020.10, 2000, "Apple");

        /* checking if values are being retrieved properly from getter methods */
        assertTrue(testElectronics.getProductID().equals("067192"));
        assertTrue(testElectronics.getDescription().equals("Really Cool Computer"));
        assertTrue(testElectronics.getPrice() == 2020.10);
        assertTrue(testElectronics.getYear() == 2000);
        assertTrue(testElectronics.getMaker().equals("Apple"));
    }

    @Test
    public void testElectronicsSetValues() {
        Electronics testElectronics = new Electronics();

        /* setting class variables to values */
        testElectronics.setProductID("067192");
        testElectronics.setDescription("Really Cool Computer");
        testElectronics.setPrice(2020.10);
        testElectronics.setYear(2000);
        testElectronics.setMaker("Apple");

        /* checking if class variables were accessed and changed properly */
        assertTrue(testElectronics.getProductID().equals("067192"));
        assertTrue(testElectronics.getDescription().equals("Really Cool Computer"));
        assertTrue(testElectronics.getPrice() == 2020.10);
        assertTrue(testElectronics.getYear() == 2000);
        assertTrue(testElectronics.getMaker().equals("Apple"));
    }
}
 