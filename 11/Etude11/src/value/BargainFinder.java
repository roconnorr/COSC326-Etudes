package value;

import java.util.*;

/**
 * The class for bargain finders.
 *
 * Rory O'Connor (9825774), Jake Redmond (9808422), Ryan Swanepoel (6535816)
 * @author MichaelAlbert
 */
public class BargainFinder {

    private SiteInfo site;
    private CustomerInfo customer;
    private int budget;
 
    private ArrayList<String> bestWithinBudget = new ArrayList<>();
    private int currentBestValue = 0;

    public BargainFinder(SiteInfo site, CustomerInfo customer, int budget) {
        this.site = site;
        this.customer = customer;
        this.budget = budget;
    }
    
    //returns the best value shopping cart
    public ArrayList<String> shoppingList() {
        findSubsets(new ArrayList<>(), customer.getItems());
        return bestWithinBudget;
    }
    
    //performs recursive dfs to find the best value card
    public void findSubsets(ArrayList<String> currentCart, ArrayList<String> itemList){
        //get the site value for the current cart
        int currentCartCost = site.getCost(currentCart);
        
        //return if not within the budget
        if (currentCartCost > this.budget) {
            return;
        }
        
        //calculate the customer value of the current cart
        int currentCartValue = 0;
        for (String item: currentCart){
            currentCartValue += customer.getValue(item);
        }
        
        //if the current value is larger than the current best
        //set the new best cart
        if (currentCartValue > this.currentBestValue){
            currentBestValue = currentCartValue;
            bestWithinBudget = new ArrayList<>(currentCart);
        }
        
        //if there are items left, try adding them
        if (!itemList.isEmpty()){
            for (int i = 0; i < itemList.size(); i++){
                //add the next item to the current cart
                currentCart.add(itemList.get(i));
                String temp = itemList.get(i);
                itemList.remove(i);
                //recurse on new cart and item list values
                findSubsets(currentCart, itemList);
                currentCart.remove(currentCart.size()-1);
                itemList.add(i, temp);
            }
        }
    }
}
