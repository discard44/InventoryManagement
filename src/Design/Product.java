package Design;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class for adding products to the inventory.
 * @author Max Perrigo
 */
public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price = 0.0;
    private int stock = 0;
    private int min;
    private int max;
    
    /**
     * Generate contructor
     * @param productID Default Value Int
     * @param name Default Value String
     * @param price Default Value Double
     * @param inStock Default Value Int
     * @param min Default Value Int
     * @param max Default Value INt
     */
    public Product(int productID, String name, double price, int inStock, int min, int max) {
        setProductID(productID);
        setName(name);
        setPrice(price);
        setInStock(inStock);
        setMin(min);
        setMax(max);
    }
    /**
     * Return list of associated parts
     * @return associated part
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
    /**
     * sets associated parts for a product
     * @param associatedParts Set List for associated parts
     */
    public void setAssociatedParts(ObservableList<Part> associatedParts) {
        this.associatedParts = associatedParts;
    }
    /**
     * Return product id
     * @return id
     */
    public int getProductID() {
        return id;
    }
    /**
     * Set product ID
     * @param productID Set Id:Int
     */
    public void setProductID(int productID) {
        this.id = productID;
    }
    /**
     * Return Name
     * @return name
     */
    public String getName() {
        return name;
    }
    /**
     * Sets the name
     * @param name Set Name:String
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * gets the price
     * @return Returns the Set Price: Double
     */
    public double getPrice() {
        return price;
    }
    /**
     * Sets the price
     * @param price Set Price:Double
     */
    public void setPrice(double price) {
        this.price = price;
    }
    /**
     * Gets the number of stock for an item.
     * @return inStock
     */
    public int getStock() {
        return stock;
    }
    /**
     * Sets number in stock
     * @param inStock Set Amount of Inventory for an Item: Int
     */
    public void setInStock(int inStock) {
        this.stock = inStock;
    }
    /**
     * Get the min value
     * @return  Return the Minimum Stock: Int
     */
    public int getMin() {
        return min;
    }
    /**
     * Sets the min value
     * @param min Set the Minimum Stock:Int
     */
    public void setMin(int min) {
        this.min = min;
    }
    /**
     * Gets the max value
     * @return max
     */
    public int getMax() {
        return max;
    }
    /**
     * Sets the max value
     * @param max Set Maximum Stock: Int
     */
    public void setMax(int max) {
        this.max = max;
    }
    /**
     * Creates the association between the part and product.
     * @param partToAdd Adds Associated parts to list.
     */
    public void addAssociatedPart(Part partToAdd) {
        associatedParts.add(partToAdd);
    }
    /**
     * Removes an association
     * @param partToRemove Removes Association by ID
     * @return Boolean value
     */
    public boolean deleteAssociatedPart(int partToRemove) {
        
        for (int i = 0; i < associatedParts.size(); i++) {
            if (associatedParts.get(i).getId() == partToRemove) {
                associatedParts.remove(i);
                return true;
            }
        }

        return false;
    }
    /**
     * Returns the associated parts list for a product.
     * @param partid Id to lookup associated part
     * @return associatedParts index 
     */
    public Part lookupAssociatedPart(int partid) {
        for (Part p : associatedParts) {
            if (p.getId() == partid) {
                return associatedParts.get(partid);
            }
        }
        return null;
    }

}
