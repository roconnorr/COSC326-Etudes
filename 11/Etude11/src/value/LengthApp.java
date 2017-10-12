package value;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Read site and customer info from command argument filenames, and test bargain
 * finder on them.
 * 
 * @author Michael Albert
 */
public class LengthApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        
        HashSet<String> items = new HashSet<>();
        items.add("a");
        items.add("b");
        items.add("c");
        items.add("aa");
        items.add("bb");
        items.add("cc");
        items.add("abcdefg");
        items.add("axcx");
        items.add("bdgdg");
        items.add("casah");
        items.add("asdhaa");
        items.add("bsadhhsb");
        items.add("cshdc");
        items.add("asdhasd");
        items.add("bsdhasd");
        items.add("casdh");
        items.add("aasdhsa");
        items.add("basdhb");
        items.add("casdhsdhc");
        items.add("asdhsa");
        items.add("sadhsdhb");
        items.add("casdh");
        items.add("aaashd");
        items.add("bsadhsab");
        items.add("cashdc");
        items.add("bsadhnb");
        items.add("casfnfa");
        items.add("aanfafa");
        items.add("bnfafaneanb");
        items.add("aanernra");
        items.add("beanraernb");
        
        LengthSite site = new LengthSite(items);
        
        HashMap<String, Integer> customerData = new HashMap<>();
        System.out.println("Values");
        for(String s : items) {
        	int v = (int) (1 + Math.random()*10);
        	System.out.println(s + " " + v);
        	customerData.put(s, v);
        }
        CustomerInfo customer = new Customer(customerData);
        System.out.println("------------------");
        for(int i = 0; i < 4; i++) {
        	int b = (int) (1 + Math.random()*30);
        	System.out.println("Budget: " + b);
        	
        
        BargainFinder bf = new BargainFinder(site, customer, b);
        for(String s: bf.shoppingList()) System.out.println(s);
        System.out.println("--------------------");
        }
        
    }
    
}
