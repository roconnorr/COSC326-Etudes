
/**
 * RedGreen.java (Java7+)
 * Calculates near factors and colours of numbers in a given range
 *
 * Rory O'Connor
 */

import java.util.*;

public class RedGreen {

    //store each number's colour in a hash table
    public static HashMap<Integer, Character> colours = new HashMap<>();
    //declare the arraylist to store near factors
    public static ArrayList<Integer> nearFactors;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        ArrayList<InputScenario> inputs = new ArrayList<InputScenario>();

        //parse input
        while (scan.hasNextLine()) {
            String input = scan.nextLine();
            if (input.length() != 0 && input.charAt(0) != '#') {
               String[] numbers = input.split(" ");
               int inputA = Integer.parseInt(numbers[0]);
               int inputB = Integer.parseInt(numbers[1]);

               inputs.add(new InputScenario(inputA, inputB));
            }
       }

        //run each input scenario
        for (InputScenario input : inputs) {

            //assign constant for 1
            colours.put(1, 'G');

            //evaluate the prime factors and colours of each number
            //from 2 to the end so that each number is assigned
            //a colour
            for (int nValue = 2; nValue <= (input.lowerBound + input.range)-1; nValue++) {
                evaluateNumber(nValue);
            }

            //if the start is 0, skip it for printing
            if(input.lowerBound == 0){
                input.lowerBound++;
            }

            //print out the R/G string of the scenario
            System.out.print("# ");
            for (int i = input.lowerBound; i < input.lowerBound + input.range; i++) {
                System.out.print(colours.get(i));
            }

            //new line between scenarios for readability
            System.out.println();
        }
    }

    /**
     * Calculates near factors of a number
     * also calls colour setting method
     */
    public static void evaluateNumber(int n) {
        int factor;
        int squareRoot = (int) Math.sqrt(n);
        nearFactors = new ArrayList<Integer>();
        //calculate factors up to the square root+1
        for (int i = 2; i < squareRoot+1; i++) {
            //calculate factor
            factor = n / i;
            nearFactors.add(factor);
        }
        //if the last near factor equals the squareRoot, decrement squareRoot
        //to avoid adding duplicates to the arraylist
        if(nearFactors.size() != 0 && 
            nearFactors.get(nearFactors.size()-1) == squareRoot){
            squareRoot--;
        }

        //all numbers <= squareRoot are near factors so add them
        fillFactors(squareRoot);

        //call the method that determines the colour of a number
        //only if the colour doesn't exist in the hash table
        Character nColour = colours.get(n);
        if(nColour == null){
            setColour(n);
        }
    }

    /**
     * Calculates the colour for a given number
     */
    public static void setColour(int n) {
        int greenCount = 0;
        int redCount = 0;

        //count the colour of each near factor
        Character factorColour;
        for (int i = 0; i<nearFactors.size(); i++) {
            factorColour = colours.get(nearFactors.get(i));
            //if over half the factors are one colour, exit the loop
            if (greenCount > nearFactors.size() / 2 || 
                redCount > nearFactors.size() / 2   ){
                break;
            }

            //add to the correct colour count
            if (factorColour == 'G') {
                greenCount++;
            } else if (factorColour == 'R'){
                redCount++;
            }
        }

        //set the colour of the number in the hash table
        if (greenCount > redCount) {
            colours.put(n, 'R');
        } else {
            colours.put(n, 'G');
        }
    }

    /**
     * Utility method to add numbers to the factors array
     * from the given number down to 1
     */
    public static void fillFactors(int start) {
        for (int x = start; x > 0; x--) {
            nearFactors.add(x);
        }
    }
}

/**
 * Class to store scenario inputs from stdin
 */
class InputScenario {

    public int lowerBound;
    public int range;

    public InputScenario(int lowerBound, int range) {
        this.lowerBound = lowerBound;
        this.range = range;
    }
}