
package value;

import java.util.ArrayList;

/**
 * Interface for the "value" side of the value étude.
 * 
 * @author MichaelAlbert
 */
public interface CustomerInfo {
    
    
    /**
     * Returns the items that the customer is interested in
     * @return An array of items the customer would like (no duplicates)
     * 
     */
    public ArrayList<String> getItems();
    
    /**
     * Returns the value to the customer of an item (negative if the item
     * is unwanted)
     * 
     * @param item the item
     * @return its value
     */
    public int getValue(String item); // Returns -1 for a non-valid item
    
}
