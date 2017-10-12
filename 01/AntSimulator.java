/**
 * AntSimulator.java (Java7+)
 * Reads ant DNA from stdin and calculates its movements
 *
 * Joe Gasparich
 * Rory O'Connor
 */

import java.util.*;

public class AntSimulator {

    private static HashMap<Square, Character> squares;

    private static boolean gridSet = false;

    private static ArrayList<Ant> ants = new ArrayList<Ant>();

    public static void main(String[] args) {
        //Take input from stdin and turn it into ant objects
        parseDNAInput();

        //Calculate the ant's movement for each turn
        for (Ant ant : ants) {
            //reset the grid with the current ant's default grid char
            clearGrid(ant.gridDefault);
            moveAnt(ant);
        }

        //Print the input string and final location for each ant
        for (Ant ant : ants) {
            printResults(ant);
        }
    }

    /**
     * Reads ant DNA from stdin, puts it into ant objects and adds it 
     * to the Ant arraylist
     *
     */
    public static void parseDNAInput() {
        Ant newAnt = new Ant();
        Scanner scan = new Scanner(System.in);

        while (scan.hasNextLine()) {
            String input = scan.nextLine();
            //if the line is blank, add the ant
            if (input.length() == 0) {
                ants.add(newAnt);
                newAnt = new Ant();
                gridSet = false;
                
            //check that the line isn't a comment
            } else if (input.charAt(0) != '#') {
                //check if the line is a digit to parse turns
                if (Character.isDigit(input.charAt(0))) {
                    newAnt.turns = Integer.parseInt(input);
                    //add input to ant's raw input string
                    newAnt.rawInput += input;
                    //if this is the last line of the file, add the ant
                    if(!scan.hasNextLine()){
                        ants.add(newAnt);
                    }
                } else {
                    //add input to ant's raw input string
                    newAnt.rawInput += input + "\n";
                    //Parse DNA
                    Character key = input.charAt(0);
                    Character[][] data = new Character[4][2];
                    for (int i = 0; i < 4; i++) {
                        data[i][0] = input.charAt(i + 2);
                        data[i][1] = input.charAt(i + 7);
                    }

                    //put ant DNA in the map
                    newAnt.dna.put(key, data);

                    //Set ant's grid variable if it has not been set before
                    if (!gridSet) {
                        newAnt.gridDefault = input.charAt(0);
                        gridSet = true;
                    }
                } 
            //if the last line is a comment, add the ant  
            } else if(input.charAt(0) == '#' && !scan.hasNextLine()) {
            	ants.add(newAnt);
            }
        }
        scan.close();
    }

    /**
     * Moves the ant on the grid according to its DNA.
     *
     */
    public static void moveAnt(Ant ant) {
        for (int i = 0; i < ant.turns; i++) {
            //Get next move
            Character gridState = squares.get(new Square(ant.x, ant.y));
            Character[][] data = ant.dna.get(gridState);
            Character nextMove = data[ant.dir][0];
            squares.put(new Square(ant.x, ant.y), data[ant.dir][1]);

            //Move Ant
            switch (nextMove) {
                case 'N':
                    ant.y += 1;
                    ant.dir = 0;
                    break;
                case 'E':
                    ant.x += 1;
                    ant.dir = 1;
                    break;
                case 'S':
                    ant.y -= 1;
                    ant.dir = 2;
                    break;
                case 'W':
                    ant.x -= 1;
                    ant.dir = 3;
                    break;
                default:
                    break;
            }

            if(squares.get(new Square(ant.x, ant.y)) == null){
                squares.put(new Square(ant.x, ant.y), ant.gridDefault);
            }
        }
    }

    /**
     * Resets the HashMap that holds the squares
     *
     */
    public static void clearGrid(Character c) {
        Square s = new Square(0, 0);
        squares = new HashMap<Square, Character>();
        squares.put(s, c);
    }

    /**
     * Prints the input dna and final coordinates of an ant.
     *
     */
    public static void printResults(Ant ant) {
        //Print ant location
        System.out.println(ant.rawInput);
        System.out.println("# " + (ant.x)
             + " " + (ant.y));
        if(ants.indexOf(ant) != (ants.size() -1)){
            System.out.println();
        }
    }
}

/**
 * Class describing an Ant Object
 *
 */
class Ant {
    public int x;
    public int y;
    public int dir = 0; //0 = N, 1 = E, 2 = S, 3 = W
    public int turns = 0;
    //stores ant data input
    public String rawInput = "";
    //stores the character to use to set the grid
    public Character gridDefault;
    public HashMap<Character, Character[][]> dna 
            = new HashMap<Character, Character[][]>();

    public Ant(){
        this.x = 0;
        this.y = 0;
    }
}

/**
 * Class describing the square an ant is on
 *
 */
class Square {
    public final int x;
    public final int y;

    public Square(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.x;
        hash = 79 * hash + this.y;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Square other = (Square) obj;
        if (this.x != other.x) {
            return false;
        }
        if (this.y != other.y) {
            return false;
        }
        return true;
    } 
}