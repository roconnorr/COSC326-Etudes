package value;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * A site whose cost is "C times the longest string in the list, 
 * plus one per item"
 *
 * @author Michael Albert
 */
public class LengthSite implements SiteInfo {
    
    public HashSet<String> items;
    public static final int C = 10;

    public LengthSite(HashSet<String> items) {
        this.items = items;
    }


    @Override
    public int getCost(ArrayList<String> list) {
        int l = 0;
        for(String item : list) {
            l = (item.length() > l) ? item.length() : l;
        }
        return C*l + list.size();
    }

}
