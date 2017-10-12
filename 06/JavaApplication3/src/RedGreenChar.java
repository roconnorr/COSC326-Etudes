
/**
 * RedGreen.java (Java7+)
 * Calculates near factors and colours of numbers in a given range
 *
 * Rory O'Connor
 */
import java.util.*;

public class RedGreenChar {

    //store each number's colour
    //public static char[] arrayColours;
    //public static HashMap<Integer, Character> colours = new HashMap<>();
    public static int[] colours = new int[10000000];
    //public static ArrayList<Integer> nearFactors;
    public static int[] nearFactors;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        ArrayList<InputScenario> inputs = new ArrayList<InputScenario>();

        //parse input
//        while (scan.hasNextLine()) {
//            String input = scan.nextLine();
//            if (input.charAt(0) != '#') {
//                String[] numbers = input.split(" ");
//                int inputA = Integer.parseInt(numbers[0]);
//                int inputB = Integer.parseInt(numbers[1]);
//
//                inputs.add(new InputScenario(inputA, inputB));
//            }
//        }

        inputs.add(new InputScenario(0, 10000000));

        //run each input scenario
        for (InputScenario input : inputs) {
            //initialize array to fit input size
            //arrayColours = new char[input.end + 1];

            //assign constant for 1
            //colours.put(1, 'G');
            colours[1] = 0;

            //evaluate the prime factors and colours of each number
            //from 2 to the end so that each number is assigned
            //a colour
            for (int nValue = 2; nValue <= input.end; nValue++) {
                evaluateNumber(nValue);
            }

            //if the start is 0, skip it for printing
            if(input.start == 0){
                input.start++;
            }

            //print out the R/G string of the scenario
            for (int i = input.start; i < input.end+1; i++) {
                //red = 2, green =1
                System.out.print(colours[i]);
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
        //System.out.println(n);
        //store near factors for current number
        //nearFactors = new ArrayList<Integer>();
        
        int factor;
        int squareRoot = (int) Math.sqrt(n);
        nearFactors = new int[(squareRoot*2)];
        //calculate factors up to the square root
        for (int i = 2; i <= squareRoot; i++) {
            //calculate factor
            factor = n / i;

            //nearFactors.add(factor);
            nearFactors[i] = factor;
        }
        //all numbers <= squareRoot are near factors so add them
        fillFactors(squareRoot);

        //call the method that determines the colour of a number
        //only if the colour doesn't exist in the hash table
        Integer nColour = colours[n];
        if(nColour == 0){
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
        Integer factorColour;
        for (int i = 1; i<nearFactors.length; i++) {
            factorColour = colours[i];

            //if over half the factors are one colour, exit the loop
            if (greenCount > nearFactors.length / 2 || redCount > nearFactors.length / 2) {
                break;
            }

            //add to the correct colour count
            if (factorColour == 'G') {
                greenCount++;
            } else if (factorColour == 'R') {
                redCount++;
            }
        }

        //set the colour of the number in the hash table
        if (greenCount > redCount) {
            colours[n] = 2; 
        } else {
            colours[n] = 1;
        }
    }

    /**
     * Utility method to add numbers to the factors array
     * from the given number down to 1
     */
    public static void fillFactors(int start) {
        for (int x = start; x > 0; x--) {
            //nearFactors.add(x);
            nearFactors[start-x] = x;
        }
    }
}

/**
 * Class to store scenario inputs from stdin
 */
//class InputScenario1 {
//
//    public int start;
//    public int end;
//
//    public InputScenario1(int start, int end) {
//        this.start = start;
//        this.end = end;
//    }
//}