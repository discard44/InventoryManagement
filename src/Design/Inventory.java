package Design;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class contains methods to work with the inventory. E.G to find parts and
 * products.
 *
 * @author Max Perrigo
 */
public class Inventory {

    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * Add a Product to the inventory.
     *
     * @param prod This is the product to add.
     */
    public static void addProduct(Product prod) {
        if (prod != null) {
            allProducts.add(prod);
        }
    }

    /**
     * Remove a product
     *
     * @param productToRemove Takes this information to remove the product.
     * @return boolean value
     */
    public static boolean deleteProduct(Product productToRemove) {
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getProductID() == productToRemove.getProductID()) {
                allProducts.remove(i);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * Method to search the product list by ID.
     *
     * @param Id Look up ID
     * @return Part
     *
     */
    public static Part lookupPart(int Id) {
        for (Part prod : allParts) {
            if (prod.getId() == Id) {
                return prod;
            }
        }
        return null;
    }

    /**
     * Searches the allproducts
     *
     * @param nameToFind Look up name
     * @return Observable List searchedList
     */
    public static ObservableList<Product> lookUpProduct(String nameToFind) {
        if (!allProducts.isEmpty()) {
            ObservableList searchProdsList = FXCollections.observableArrayList();
            for (Product prod : getAllProducts()) {
                if (prod.getName().contains(nameToFind)) {
                    searchProdsList.add(prod);
                }
            }
            return searchProdsList;
        }
        return null;
    }

    /**
     * Takes a new product and updates the Inventory
     *
     * @param id id to update
     * @param newProd prod to update.
     */
    public static void updateProduct(int id, Product newProd) {
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getProductID() == id) {
                allProducts.set(i, newProd);
                break;
            }
        }
    }

    /**
     * Add a part
     *
     * @param part Part Object to be added.
     */
    public static void addPart(Part part) {
        if (part != null) {
            allParts.add(part);
        }
    }

    /**
     * Deletes a part from allParts
     *
     * @param part Received to delete
     * @return Boolean value
     */
    public static boolean deletePart(Part part) {
        for (int i = 0;i < allParts.size(); i++) {
            if (allParts.get(i).getId() == part.getId()) {
                allParts.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Look up product by ID
     *
     * @param productID Product ID number to find a product.
     * @return Product from the list.
     */
    public static Product lookupProduct(int productID) {
        for (Product p : allProducts) {
            if (p.getProductID() == productID) {
                return p;
            }
        }
        return null;
    }

    /**
     * Look up part by ID
     *
     * @param nameToLookUp Receive to lookup a part.
     * @return Observable List
     */
    public static ObservableList<Part> lookUpPart(String nameToLookUp) {
        if (!allParts.isEmpty()) {
            ObservableList searchedList = FXCollections.observableArrayList();
            for (Part part : getAllParts()) {
                if (part.getName().contains(nameToLookUp)) {
                    searchedList.add(part);
                }
            }
            return searchedList;
        }
        return null;
    }

    /**
     * Takes a part and updates the list
     *
     * @param id ID to update.
     * @param partToUpdate Part received to update a part.
     */
    public static void updatePart(int id, Part partToUpdate) {
        for (int i = 0; i <= allParts.size(); i++) {
            if (allParts.get(i).getId() == partToUpdate.getId()) {
                allParts.set(i, partToUpdate);
                break;
            }
        }

    }

    /**
     * Returns all products
     *
     * @return allProducts
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * Returns all Parts
     *
     * @return allParts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

}
