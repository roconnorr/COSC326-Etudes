package value;

import java.util.ArrayList;

/**
 * Interface for the "cost" side of the value proposition étude.
 *
 * @author Michael Albert
 */
public interface SiteInfo {
    
    /**
     * Returns the cost of an array of items. Return value will be negative
     * if one or more of the items is unavailable.
     * 
     * @param items The items to be purchased
     * @return The cost of purchasing those items
     */ 
    public int getCost(ArrayList<String> items);
}
