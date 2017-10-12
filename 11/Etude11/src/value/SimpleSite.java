package value;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Michael Albert
 */
public class SimpleSite implements SiteInfo {
    
    public HashMap<String, Integer> cost;

    public SimpleSite(HashMap<String, Integer> cost) {
        this.cost = cost;
    }
    
    
    @Override
    public int getCost(ArrayList<String> items) {
        int totalCost = 0;
        for(String item: items) {
            totalCost += cost.get(item);
        }
        return totalCost;
    }

}
