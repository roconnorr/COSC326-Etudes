package value;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author MichaelAlbert
 */
public class Customer implements CustomerInfo {
    
    HashMap<String, Integer> values;

    public Customer(HashMap<String, Integer> values) {
        this.values = values;
    }

    @Override
    public ArrayList<String> getItems() {
        ArrayList<String> result = new ArrayList<>();
        result.addAll(values.keySet());
        return result;
    }

    @Override
    public int getValue(String item) {
        return values.get(item);
    }

}
