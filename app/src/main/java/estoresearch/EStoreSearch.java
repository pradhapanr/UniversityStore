package estoresearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Iterator;

public class EStoreSearch {

    private ArrayList<Product> productList;
    private HashMap<String, ArrayList<Integer>> descriptionMap;

    public EStoreSearch() {
        productList = new ArrayList<Product>();
        descriptionMap = new HashMap<String, ArrayList<Integer>>();
    }

    public boolean checkValidProductID(String productID) {
        for (int i = 0; i < productList.size(); i++) {
            Product temp = productList.get(i);
            if (temp.getProductID().equals(productID)) {
                return false;
            }
        }
        return true;
    }

    public void updateDescriptionMap() {
        ArrayList<Product> productListCopy = new ArrayList<Product>(productList);
        int size = productListCopy.size();
        String[] descriptionWords = productListCopy.get(size - 1).getDescription().split(" ");

        for (int i = 0; i < descriptionWords.length; i++) {
            ArrayList<Integer> indexList = descriptionMap.get(descriptionWords[i]);
            ArrayList<Integer> tempList = new ArrayList<Integer>();
            if (indexList == null) {
                tempList.add(size - 1);
                descriptionMap.put(descriptionWords[i], tempList);
            } else {
                indexList.add(size - 1);
                descriptionMap.replace(descriptionWords[i], indexList);
            }
        }
    }

    public HashMap<String, ArrayList<Integer>> generateMap(ArrayList<Product> productList) {
        int size = productList.size();

        HashMap<String, ArrayList<Integer>> wordMap = new HashMap<String, ArrayList<Integer>>();

        for (int i = 0; i < productList.size(); i++) {
            String[] descriptionWords = productList.get(i).getDescription().split(" ");
            for (int j = 0; j < descriptionWords.length; j++) {
                ArrayList<Integer> indexList = wordMap.get(descriptionWords[j]);
                ArrayList<Integer> newList = new ArrayList<Integer>();
                if (indexList == null) {
                    newList.add(i);
                    LinkedHashSet<Integer> tempSet = new LinkedHashSet<Integer>(newList);
                    newList = new ArrayList<Integer>(tempSet);
                    wordMap.put(descriptionWords[j], newList);
                } else {
                    indexList.add(i);
                    LinkedHashSet<Integer> tempSet = new LinkedHashSet<Integer>(indexList);
                    indexList = new ArrayList<Integer>(tempSet);
                    wordMap.replace(descriptionWords[j], indexList);
                }
            }
        }
        return wordMap;
    }

    public ArrayList<Integer> getMapIndexList(String searchTerm) {
        return descriptionMap.get(searchTerm);
    }

    public ArrayList<Product> searchProducts(String productID, String searchWords[], String timePeriod) {
        ArrayList<Product> finalSearches = new ArrayList<Product>(productList);
        boolean searchWordPass = false;
        boolean searchPhrasePass = true;

        /* filters out all electronics that dont have searched for product ID */

        if (!(productID.equals(""))) {
            Iterator i = finalSearches.iterator();
            while (i.hasNext()) {
                Product temp = (Product)i.next();
                if (!(temp.getProductID().equals(productID))) {
                    i.remove();
                }
            }
        }

        /* filters out all electronics with descriptions that dont include all search words */

        HashMap<String, ArrayList<Integer>> wordMap = generateMap(finalSearches);

        // if (!(searchWords[0].equals(""))) {
        //     for (int i = 0; i < finalSearches.size(); i++) {
        //         Product temp = finalSearches.get(i); //electronics objects in finalSearches list
        //         String descriptionWords[] = temp.getDescription().toLowerCase().split("\\W+");
        //         for (int j = 0; j < searchWords.length; j++) {;
        //             for (int k = 0; k < descriptionWords.length; k++) {
        //                 //checks the current searchWord against every word in the description
        //                 if (descriptionWords[k].equals(searchWords[j])) {
        //                     searchWordPass = true;
        //                 }
        //             }
        //             if (searchWordPass == false) {
        //                 searchPhrasePass = false;
        //                 break;
        //             }
        //             searchWordPass = false;
        //         }
        //         if (searchPhrasePass == false) {
        //             finalSearches.remove(i);
        //         }
        //         searchPhrasePass = true;
        //     }
        // }


        

        if (searchWords[0].isEmpty()) { //no search phrase inputted
            //nothing happens, no word search occurs
        } else if (searchWords[0].isEmpty() == false && searchWords.length == 1) { //one word search phrase
            if (wordMap.get(searchWords[0]) == null) {
                finalSearches.clear();
            } else {
                ArrayList<Integer> finalList = new ArrayList<Integer>(wordMap.get(searchWords[0]));
                ArrayList<Product> remainingSearches = new ArrayList<Product>();
                for (int i = 0; i < finalList.size(); i++) {
                    remainingSearches.add(finalSearches.get(finalList.get(i)));
                }
                finalSearches = new ArrayList<Product>(remainingSearches);
            }
        } else if (searchWords.length >= 2) { //two plus word search phrase
            if (wordMap.get(searchWords[0]) == null) {
                finalSearches.clear();
            } else {
                ArrayList<Integer> finalList = new ArrayList<Integer>(wordMap.get(searchWords[0]));
                boolean wordExists = false;
                boolean badSearch = false;
                for (int i = 1; i < searchWords.length; i++) {
                    ArrayList<Integer> compareList = new ArrayList<Integer>(wordMap.get(searchWords[i]));
                    //compare finalList and compareList
                    for (int j = 0; j < finalList.size(); j++) {
                        for (int k = 0; k < compareList.size(); k++) {
                            if (finalList.get(j) == null || compareList.get(k) == null) { //a search word doesnt exist in map
                                badSearch = true;
                                break;
                            }
                            if (finalList.get(j) == compareList.get(k)) {
                                wordExists = true;
                                break;
                            }
                        }
                        if (badSearch == true) {
                            finalSearches.clear();
                            break;
                        }
                        if (wordExists == false) {
                            finalSearches.remove(finalList.get(j));
                        }
                        wordExists = false;
                    }
                    if (badSearch == true) {
                    break;
                    }
                }
            }
        }

        /* filters out all electronics that are not within the time period */

        //case 1: individual year (2010), case 2: range of years (2010-2020), case 3: years >= (2010-), case 4: years <= (-2020)

        if (timePeriod != null && !(timePeriod.equals(""))) { //if timePeriod is used as a search term
            int min = 0;
            int max = 0;
            String year1, year2;
            boolean check = true;

            if (timePeriod.indexOf('-') == -1) { //case 1
                min = Integer.parseInt(timePeriod);
                max = min;
            } else if (timePeriod.indexOf('-') != 0 && timePeriod.indexOf('-') != timePeriod.length() - 1) { //case 2
                year1 = timePeriod.substring(0, 4);
                year2 = timePeriod.substring(5, timePeriod.length());
                min = Integer.parseInt(year1);
                max = Integer.parseInt(year2);
            } else if (timePeriod.indexOf('-') == timePeriod.length() - 1) { //case 3
                year1 = timePeriod.substring(0, 4);
                min = Integer.parseInt(year1);
                max = 9999;
            } else if (timePeriod.indexOf('-') == 0) {//case 4
                year2 = timePeriod.substring(1, 5);
                max = Integer.parseInt(year2);
                min = 1000;
            } else {
                System.out.println("Inputted time period is not formatted correctly. Refer to the README for instructions");
                System.out.println("on how to input time period. Time period will not be used to filter search results.");
                check = false;
            }
            
            if (check == true) {
                for (int i = 0; i < finalSearches.size(); i++) {
                    Product temp = finalSearches.get(i); //object in finalSearches list
                    if (!(min <= temp.getYear() && max >= temp.getYear())) {
                        finalSearches.remove(i);
                    }
                }
            }
        }

        return finalSearches;

    }

    private ArrayList<Integer> compareLists(ArrayList<Integer> arr1, ArrayList<Integer> arr2, ArrayList<Product> finalSearches) {
        return null;
    }

    public void addProduct(Book newBook) {
        this.productList.add(newBook);
        updateDescriptionMap();
    }

    public void addProduct(Electronics newElectronics) {
        this.productList.add(newElectronics);
        updateDescriptionMap();
    }

    public ArrayList<Product> getProductList() {
        return this.productList;
    }

    public void printHashMap() {
        System.out.println(descriptionMap);
    }


}