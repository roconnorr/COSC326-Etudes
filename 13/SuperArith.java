import java.util.ArrayList;
import java.util.Scanner;

/**
 * An arithmetic class designed to allow arbitrary-precision integers. Now with prefix and postfix support!
 * @author Jake Redmond 9808422, Ryan Swanepoel 6535816, Rory O'Connor 9825774
 * @version 9/21/2017
 */
@SuppressWarnings("rawtypes")
public class SuperArith implements Comparable {
    private ArrayList<Integer> wowBaseTen;
    private boolean isNegative;
    
    public static void main (String args[]) {
        Scanner scanner = new Scanner(System.in);
        String[] line;
        SuperArith shell = new SuperArith("0"); // so we can call our methods
        String format;
        SuperArith[] results;
        while (scanner.hasNext()) {
            line = scanner.nextLine().split(" ");
            if (line.length != 3) System.out.println("# Syntax error");
            else if (!line[0].equals("#")) {
                    
                // check form of input: Polish, Reverse Polish, or normal
                format = determineFormat(line);
                if (format.equals("Prefix")) {
                    for (int i = 0; i < line.length; i++) {
                        System.out.print(line[i] + " ");
                    }
                    System.out.println("Prefix --> " + line[1] + " " + line[0] + " " + line[2]+ " Infix");
                    
                    // calculate
                    if (line[0].equals("+")) {
                        System.out.println("# " + shell.add(new SuperArith(line[1]), new SuperArith(line[2])));
                    }
                    else if (line[0].equals("-")) {
                        System.out.println("# " + shell.subtract(new SuperArith(line[1]), new SuperArith(line[2])));
                    }
                    else if (line[0].equals("*")) {
                        System.out.println("# " + shell.multiply(new SuperArith(line[1]), new SuperArith(line[2])));
                    }
                    else if (line[0].equals("/")) {
                        results = shell.divide(new SuperArith(line[1]), new SuperArith(line[2]));
                        System.out.println("# " + results[0] + " " + results[1]);
                    }
                    else if (line[0].equals("gcd")) {
                        System.out.println("# " + shell.gcd(new SuperArith(line[1]), new SuperArith(line[2])));
                    }
                    else if (line[0].equals(">")) {
                        if (new SuperArith(line[1]).compareTo(new SuperArith(line[2])) == 1 ) System.out.println("# true");
                        else System.out.println("# false");
                    }
                    else if (line[0].equals("<")) {
                        if (new SuperArith(line[1]).compareTo(new SuperArith(line[2])) == -1 ) System.out.println("# true");
                        else System.out.println("# false");
                    }    
                    else if (line[0].equals("=")) {
                        if (new SuperArith(line[1]).compareTo(new SuperArith(line[2])) == 0 ) System.out.println("# true");
                        else System.out.println("# false");
                    }
                    else System.out.println("# Syntax error");
                        
                        
                        
                    }
                    else if (format.equals("Normal")) {
                        System.out.println(line[0] + " " + line[1] + " " + line[2] + " Infix");
                        if (!_isNum(line[2])) {
                            System.out.println("# Syntax error");
                            continue;
                        }
                        // calculate
                        if (line[1].equals("+")) {
                            System.out.println("# " + shell.add(new SuperArith(line[0]), new SuperArith(line[2])));
                        }
                        else if (line[1].equals("-")) {
                            System.out.println("# " + shell.subtract(new SuperArith(line[0]), new SuperArith(line[2])));
                        }
                        else if (line[1].equals("*")) {
                            System.out.println("# " + shell.multiply(new SuperArith(line[0]), new SuperArith(line[2])));
                        }
                        else if (line[1].equals("/")) {
                            results = shell.divide(new SuperArith(line[0]), new SuperArith(line[2]));
                            System.out.println("# " + results[0] + " " + results[1]);
                        }
                        else if (line[1].equals("gcd")) {
                            System.out.println("# " + shell.gcd(new SuperArith(line[0]), new SuperArith(line[2])));
                        }
                        else if (line[1].equals(">")) {
                            if (new SuperArith(line[0]).compareTo(new SuperArith(line[2])) == 1 ) System.out.println("# true");
                            else System.out.println("# false");
                        }
                        else if (line[1].equals("<")) {
                            if (new SuperArith(line[0]).compareTo(new SuperArith(line[2])) == -1 ) System.out.println("# true");
                            else System.out.println("# false");
                        }    
                        else if (line[1].equals("=")) {
                            if (new SuperArith(line[0]).compareTo(new SuperArith(line[2])) == 0 ) System.out.println("# true");
                            else System.out.println("# false");
                        }
                        else System.out.println("# Syntax error");
                    }
                    else if (format.equals("Postfix")) {
                        for (int i = 0; i < line.length; i++) {
                            System.out.print(line[i] + " ");
                        }
                        System.out.println("Postfix --> " + line[0] + " " + line[2] + " " + line[1] + " Infix");
                        
                        // calculate
                        if (line[2].equals("+")) {
                            System.out.println("# " + shell.add(new SuperArith(line[0]), new SuperArith(line[1])));
                        }
                        else if (line[2].equals("-")) {
                            System.out.println("# " + shell.subtract(new SuperArith(line[0]), new SuperArith(line[1])));
                        }
                        else if (line[2].equals("*")) {
                            System.out.println("# " + shell.multiply(new SuperArith(line[0]), new SuperArith(line[1])));
                        }
                        else if (line[2].equals("/")) {
                            results = shell.divide(new SuperArith(line[0]), new SuperArith(line[1]));
                            System.out.println("# " + results[0] + " " + results[1]);
                        }
                        else if (line[2].equals("gcd")) {
                            System.out.println("# " + shell.gcd(new SuperArith(line[0]), new SuperArith(line[1])));
                        }
                        else if (line[2].equals(">")) {
                            if (new SuperArith(line[0]).compareTo(new SuperArith(line[1])) == 1 ) System.out.println("# true");
                            else System.out.println("# false");
                        }
                        else if (line[2].equals("<")) {
                            if (new SuperArith(line[0]).compareTo(new SuperArith(line[1])) == -1 ) System.out.println("# true");
                            else System.out.println("# false");
                        }    
                        else if (line[2].equals("=")) {
                            if (new SuperArith(line[0]).compareTo(new SuperArith(line[1])) == 0 ) System.out.println("# true");
                            else System.out.println("# false");
                        }
                        else System.out.println("# Syntax error");
                    }
                }
        }
        scanner.close();
    }
    
    
    /**
     * Constructor for SuperArith.
     * @param num    the String that will be converted into the stored number
     */
    public SuperArith(String num) {
        wowBaseTen = new ArrayList<Integer>(num.length());
        for (int i = num.length()-1; i > 0; i--) {
            wowBaseTen.add(Character.getNumericValue((num.charAt(i))));
        }
        if (num.length() == 0) {
            isNegative = false;
            return;
        }
        if (num.charAt(0) == '-') isNegative = true;
        else {
            wowBaseTen.add(Character.getNumericValue((num.charAt(0))));
            isNegative = false;
        }
    }
    
    
    /**
     * Compares two SuperArith objects.
     * @param arg0        the object to be compared to
     * @return            1 if arg0 is lesser, 0 if arg0 is the same, and -1 if arg0 is greater
     */
    @Override
    public int compareTo(Object arg0) {
        SuperArith arg = ((SuperArith) arg0);
        // check signs first
        if (!isNegative) {
            // check the opposite sign
            if (arg.getSign()) return 1;
            // check the length of the two numbers
            if (wowBaseTen.size() > arg.wowBaseTen.size()) return 1;
            if (wowBaseTen.size() < arg.wowBaseTen.size()) return -1;
            // same length and sign; check number-by-number
            for (int i = wowBaseTen.size()-1; i >= 0; i--) {
                if (wowBaseTen.get(i) > arg.wowBaseTen.get(i)) return 1;
                if (wowBaseTen.get(i) < arg.wowBaseTen.get(i)) return -1;
            }
        }
        // opposite; checking when numbers are negative
        else {
            if (!arg.getSign()) return -1;
            if (wowBaseTen.size() < arg.wowBaseTen.size()) return 1;
            if (wowBaseTen.size() > arg.wowBaseTen.size()) return -1;
            for (int i = wowBaseTen.size()-1; i >= 0; i--) {
                if (wowBaseTen.get(i) > arg.wowBaseTen.get(i)) return -1;
                if (wowBaseTen.get(i) < arg.wowBaseTen.get(i)) return 1;
            }
        }
        return 0;
    }
    
    
    /**
     * Compares this SuperArith oject to another as though the numbers they store are unsigned.
     * @param arg0        the SuperArith object to compare against
     * @return            -1 if the other object is greater, 0 if they are the same, 1 is this object is greater
     */
    private int compareToUnsigned(Object arg0) {
        SuperArith arg = ((SuperArith) arg0);
        if (wowBaseTen.size() > arg.wowBaseTen.size()) return 1;
        if (wowBaseTen.size() < arg.wowBaseTen.size()) return -1;
        // same length and sign; check number-by-number
        for (int i = wowBaseTen.size()-1; i >= 0; i--) {
            if (wowBaseTen.get(i) > arg.wowBaseTen.get(i)) return 1;
            if (wowBaseTen.get(i) < arg.wowBaseTen.get(i)) return -1;
        }
        return 0;
    }
    
    
    /**
     * A shell method for the actual addition method; handles edge cases.
     * @param a        the augend
     * @param b        the addend
     * @return        the sum of a + b
     */
    public SuperArith add (SuperArith a, SuperArith b) {
        // check the signs are compatible for addition
        if (a.getSign() != b.getSign()) {
            if (!a.isNegative) {
                b = invertSign(b);
                return doSub(a, b);
            }
            else {
                a = invertSign(a);
                return doSub(b, a);
            }
        }
        if (!a.getSign()) return doAdd(a,b);
        return invertSign(doAdd(a,b));
        
    }
    
    
    /**
     * Does the actual addition.
     * @param a        the augend
     * @param b        the addend
     * @return        the sum of a + b
     */
    private SuperArith doAdd (SuperArith a, SuperArith b) {
        SuperArith result = new SuperArith("");
        if (a.compareToUnsigned(b) == -1) return doAdd(b, a);
        while (b.wowBaseTen.size() < a.wowBaseTen.size()) b.wowBaseTen.add(0);
        for (int i = 0; i < a.wowBaseTen.size(); i++) {
            result.wowBaseTen.add(a.wowBaseTen.get(i) + b.wowBaseTen.get(i));
        }
        result.updateVals();
        return result;
    }
    
    
    /**
     * A shell method for the actual subtraction method; handles edge cases.
     * @param a        the minuend
     * @param b        the subtrahend
     * @return        the difference of a - b
     */
    public SuperArith subtract (SuperArith a, SuperArith b) {
        // check the signs are compatible for addition
        if (a.getSign() != b.getSign()) {
            if (!a.isNegative) {
                return doAdd(a, b);
            }
            else {
                return invertSign(doAdd(a, b));
            }
        }
        if (!a.getSign()) return doSub(a,b);
        return invertSign(doSub(a,b));
    }
    
    /**
     * Performs the actual subtraction.
     * @param a        the minuend
     * @param b        the subtrahend
     * @return        the difference of a - b
     */
    private SuperArith doSub (SuperArith a, SuperArith b) {
        SuperArith result = new SuperArith("");
        if (a.compareToUnsigned(b) == -1) return invertSign(doSub(b, a));
        while (b.wowBaseTen.size() < a.wowBaseTen.size()) b.wowBaseTen.add(0);
        for (int i = 0; i < a.wowBaseTen.size(); i++) {
            result.wowBaseTen.add(a.wowBaseTen.get(i) - b.wowBaseTen.get(i));
        }
        result.updateVals();
        return result;
    }
    
    /**
     * Multiplies the numbers by adding a number a to itself b times.
     * @param a        the multiplicand
     * @param b        the multiplier
     * @return        the product of a * b
     */
    public SuperArith multiply (SuperArith a, SuperArith b) {
        if (a.compareTo(b) == -1) multiply (b, a);
        
        int sign = 0;
        if (a.getSign()) {
            sign++;
            a = invertSign(a);
        }
        if (b.getSign()) {
            sign++;
            b = invertSign(b);
        }
        
        // initialize
        SuperArith zero = new SuperArith("0");
        SuperArith one = new SuperArith ("1");
        SuperArith result = a;

        // edge case: either value is 0
        if (a.compareTo(zero) == 0 || b.compareTo(zero) == 0) return new SuperArith("0");
        
        for (SuperArith i = subtract(b, one); i.compareTo(zero) > 0; i = subtract(i, one)) {
            result = doAdd(result, a);
            // this extra call to updateVals() fixed an error for some reason
            one.updateVals();
        }
        // check for a negative product
        //System.out.println(a + " " + b);
        //if (a.getSign() == b.getSign()) result.isNegative = false;
        //else result.isNegative = true;
        if (sign == 1) result = invertSign(result);
        return result;
    }
    
    
    
    
    
    public SuperArith[] divide (SuperArith a, SuperArith b) {
        SuperArith zero = new SuperArith("0");
        SuperArith one = new SuperArith ("1");
        SuperArith rem = a;
        SuperArith counter = zero;
        
        // check for zero
        if (b.compareTo(zero) == 0) {
            System.out.println("Error: Division by zero");
            return null;
        }
        
        int rem_sign = 0;
        int sign = 0;
        if (a.getSign()) {
            sign++;
            rem_sign++;
            a = invertSign(a);
        }
        if (b.getSign()) {
            sign++;
            b = invertSign(b);
        }
        
        
        while (doSub(rem, b).compareTo(zero) >= 0) {
            counter = doAdd(counter, one);
            rem = doSub(rem, b);
            b.updateVals();
            rem.updateVals();
        }
        
        if (sign == 1) counter = invertSign(counter);
        if (rem_sign == 1) rem = invertSign(rem);
        rem.updateVals();        // called once out here to clean up the output
        
        return new SuperArith[] {counter, rem};
    }
    
    
    public SuperArith half (SuperArith a) {
        return divide(a, new SuperArith ("2"))[0];
    }
    
    
    
    public SuperArith gcd (SuperArith a, SuperArith b) {
        SuperArith zero = new SuperArith("0");
        while(true) {
            SuperArith rem = a.divide(a, b)[1];
            if (rem.compareTo(zero) == 0) {
                return b;
            }
            a = b;
            b = rem;
        }
    }
    
    
    /**
     * Retrieves the sign of the current SuperArith object.
     * @return        true if the number is less than 0
     */
    public boolean getSign() {
        return isNegative;
    }
    
    /**
     * Returns a String representing the object.
     * @return        
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (isNegative == true) builder.append("-");
        for (int i = wowBaseTen.size()-1; i >= 0; i--) {
            builder.append(wowBaseTen.get(i));
        }
        return builder.toString();
    }

    /**
     * Goes through the ArrayList containing the number and alters values to in each slot
     * to be in the range [0, 10).
     */
    private void updateVals() {
        for (int i = 0; i < wowBaseTen.size() - 1; i++) {
            if (wowBaseTen.get(i) > 9) {
                wowBaseTen.set(i, wowBaseTen.get(i) - 10);
                wowBaseTen.set(i + 1, wowBaseTen.get(i + 1) + 1);
            }
            else if (wowBaseTen.get(i) < 0) {
                wowBaseTen.set(i, wowBaseTen.get(i) + 10);
                wowBaseTen.set(i + 1, wowBaseTen.get(i + 1) - 1);
            }
        }
        if (wowBaseTen.get(wowBaseTen.size()-1) > 9) {
            wowBaseTen.set(wowBaseTen.size() - 1, wowBaseTen.get(wowBaseTen.size() - 1) - 10);
            wowBaseTen.add(1);
        }
        // remove trailing 0s
        while (wowBaseTen.size() > 1) {
                if (wowBaseTen.get(wowBaseTen.size() - 1) == 0) wowBaseTen.remove(wowBaseTen.size() - 1);
                else return;
        }
    }
    
    /**
     * Reverses the isNegative value of the SuperArith object.
     * @return        the opposite of the current isNegative value
     */
    private SuperArith invertSign(SuperArith a) {
        a.isNegative = !a.isNegative;
        return a;
    }
    
    
    /**
     * Determines the format of the input mathematical function 
     * @param input        the input to analyze
     * @return            "Polish", "Reverse Polish", "Normal", or "Error"
     */
    private static String determineFormat (String[] input) {
        String format;
        try {
            Integer.parseInt(input[0]);
            if (_isNum(input[1])) format = "Postfix";
            else format = "Normal";
            
        } catch (NumberFormatException e) {
            if (input[0].equals("+") || input[0].equals("-") || input[0].equals("*") || input[0].equals("/") || input[0].equals(">") || input[0].equals("<") || input[0].equals("=") || input[0].equals("gcd")) format = "Prefix";
            else format = "Error";
        }
        
        return format;
    }
    
    private static boolean _isNum (String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
